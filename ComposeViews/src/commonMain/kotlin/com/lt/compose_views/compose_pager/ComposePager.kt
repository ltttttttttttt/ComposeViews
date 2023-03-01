/*
 * Copyright lt 2023
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lt.compose_views.compose_pager

import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import com.lt.compose_views.banner.BannerScope
import com.lt.compose_views.util.DragInteractionSource
import com.lt.compose_views.util.midOf
import com.lt.compose_views.util.rememberMutableStateOf
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * creator: lt  2022/6/25  lt.dygzs@qq.com
 * effect : 类似于安卓中的ViewPager
 *          Equivalent to the ViewPager in android
 * warning:
 * @param pageCount 一共有多少页
 *                  Sum page count
 * @param modifier 修饰
 * @param composePagerState ComposePager的状态
 *                          ComposePager's state
 * @param orientation 滑动的方向
 *                    Scroll orientation
 * @param userEnable 用户是否可以滑动,等于false时用户滑动无反应,但代码可以执行翻页
 *                   Whether the user can scroll
 * @param pageCache 左右两边的页面缓存,默认左右各缓存1页,但不能少于1页(不宜过大)
 *                  The number of pagers cached on the left and right sides
 * @param scrollableInteractionSource 滚动状态监听,可以用来监听:用户开始(结束,取消)滑动等事件
 *                                    Scroll state monitor
 * @param content compose内容区域
 *                Content of compose
 */
@Composable
fun ComposePager(
    pageCount: Int,
    modifier: Modifier = Modifier,
    composePagerState: ComposePagerState = rememberComposePagerState(),
    orientation: Orientation = Orientation.Horizontal,
    userEnable: Boolean = true,
    pageCache: Int = 1,
    scrollableInteractionSource: DragInteractionSource? = null,
    content: @Composable ComposePagerScope.() -> Unit
) {
    val indexToKey = LocalIndexToKey.current
    //key和content的缓存位置
    val contentList = remember {
        mutableStateListOf<ComposePagerContentBean>()
    }
    //content最大缓存的数量
    val maxContent by remember(key1 = pageCache) {
        mutableStateOf(pageCache * 2 + 1)
    }
    //下一个要被替换的content缓存的索引
    var nextContentReplaceIndex by remember(key1 = pageCache, key2 = pageCount) {
        mutableStateOf<Int?>(null)
    }
    var isNextPage by rememberMutableStateOf<PageChangeAnimFlag>(PageChangeAnimFlag.Reduction)
    val coroutineScope = rememberCoroutineScope()
    //检查索引是否在页数内
    remember(key1 = pageCount) {
        if (pageCount <= 0) {
        } else if (pageCount <= composePagerState.getCurrSelectIndex()) {
            composePagerState.currSelectIndex.value = pageCount - 1
            composePagerState.setPageIndex(pageCount - 1)
        }
        0
    }
    if (pageCount <= composePagerState.getCurrSelectIndex())
        return

    //初始化content
    remember(
        key1 = pageCount,
        key2 = pageCache,
    ) {
        initContentList(
            composePagerState,
            pageCache,
            indexToKey,
            contentList,
            pageCount,
            content
        )
        0
    }
    //放置的compose元素的content
    remember(
        key1 = pageCount,
        key2 = isNextPage,
        key3 = pageCache,
    ) {
        if (isNextPage is PageChangeAnimFlag.GoToPageNotAnim || isNextPage is PageChangeAnimFlag.GoToPageWithAnim) {
            initContentList(
                composePagerState,
                pageCache,
                indexToKey,
                contentList,
                pageCount,
                content
            )
            nextContentReplaceIndex = null
        }
        if (isNextPage == PageChangeAnimFlag.Next) {
            val currIndex = nextContentReplaceIndex?.let {
                if (it >= maxContent - 1)
                    0
                else
                    it + 1
            } ?: 0
            val index = composePagerState.getCurrSelectIndex() + pageCache
            val key = indexToKey(index)
            contentList[currIndex] = ComposePagerContentBean(
                key,
                Modifier.layoutId(index),
                ComposePagerScope(key)
            ) { mModifier, mScope ->
                if (key < 0 || key >= pageCount)
                    Box(modifier = Modifier)
                else {
                    Box(modifier = mModifier) {
                        mScope.content()
                    }
                }
            }
            nextContentReplaceIndex = currIndex
        } else if (isNextPage == PageChangeAnimFlag.Prev) {
            val currIndex = nextContentReplaceIndex ?: (maxContent - 1)
            val index = composePagerState.getCurrSelectIndex() - pageCache
            val key = indexToKey(index)
            contentList[currIndex] = ComposePagerContentBean(
                key,
                Modifier.layoutId(index),
                ComposePagerScope(key)
            ) { mModifier, mScope ->
                if (key < 0 || key >= pageCount)
                    Box(modifier = Modifier)
                else {
                    Box(modifier = mModifier) {
                        mScope.content()
                    }
                }
            }
            nextContentReplaceIndex = if (currIndex <= 0)
                maxContent - 1
            else
                currIndex - 1
        }
        isNextPage = PageChangeAnimFlag.Reduction
        0
    }
    val minOffset = remember(
        key1 = composePagerState.mainAxisSize,
        key2 = composePagerState.currSelectIndex.value,
        key3 = pageCount
    ) {
        val currIndex = composePagerState.currSelectIndex.value
        if (currIndex + 1 >= pageCount)
            currIndex * -composePagerState.mainAxisSize.toFloat()
        else
            (currIndex + 1) * -composePagerState.mainAxisSize.toFloat()
    }
    val maxOffset = remember(
        key1 = composePagerState.mainAxisSize,
        key2 = composePagerState.currSelectIndex.value,
    ) {
        val currIndex = composePagerState.currSelectIndex.value
        if (currIndex <= 0)
            0f
        else
            (currIndex - 1) * -composePagerState.mainAxisSize.toFloat()
    }
    //滑动监听
    val scrollableState = rememberScrollableState {
        //停止之前的动画
        composePagerState.pageChangeAnimFlag = null
        val lastOffset = composePagerState.offsetAnim.value
        val offset = midOf(minOffset, lastOffset + it, maxOffset)
        coroutineScope.launch {
            composePagerState.offsetAnim.snapTo(offset)
        }
        offset - lastOffset
    }

    //处理翻页动画
    LaunchedEffect(
        key1 = composePagerState.pageChangeAnimFlag,
        block = {
            val flag = composePagerState.pageChangeAnimFlag
            if (flag == null) {
                if (composePagerState.offsetAnim.isRunning)
                    composePagerState.offsetAnim.stop()
                return@LaunchedEffect
            }
            var returnPageChangeAnimFlag: PageChangeAnimFlag? = null
            try {
                val index = composePagerState.currSelectIndex.value
                when (flag) {
                    PageChangeAnimFlag.Prev -> {
                        if (index <= 0) {
                            composePagerState.offsetAnim.animateTo(-index * composePagerState.mainAxisSize.toFloat())
                            return@LaunchedEffect
                        }
                        try {
                            composePagerState.offsetAnim.animateTo(-(index - 1) * composePagerState.mainAxisSize.toFloat())
                        } finally {
                            composePagerState.currSelectIndex.value = index - 1
                            isNextPage = PageChangeAnimFlag.Prev
                        }
                    }

                    PageChangeAnimFlag.Next -> {
                        if (index + 1 >= pageCount) {
                            composePagerState.offsetAnim.animateTo(-index * composePagerState.mainAxisSize.toFloat())
                            return@LaunchedEffect
                        }
                        try {
                            composePagerState.offsetAnim.animateTo(-(index + 1) * composePagerState.mainAxisSize.toFloat())
                        } finally {
                            composePagerState.currSelectIndex.value = index + 1
                            isNextPage = PageChangeAnimFlag.Next
                        }
                    }

                    PageChangeAnimFlag.Reduction -> {
                        composePagerState.offsetAnim.animateTo(-index * composePagerState.mainAxisSize.toFloat())
                    }

                    is PageChangeAnimFlag.GoToPageNotAnim -> {
                        composePagerState.currSelectIndex.value = flag.index
                        composePagerState.offsetAnim.snapTo(-flag.index * composePagerState.mainAxisSize.toFloat())
                        isNextPage = flag
                    }

                    is PageChangeAnimFlag.GoToPageWithAnim -> {
                        val (goToIndex, pageChangeAnimFlag) = if (flag.index > composePagerState.currSelectIndex.value)
                            flag.index - 1 to PageChangeAnimFlag.Next
                        else
                            flag.index + 1 to PageChangeAnimFlag.Prev
                        composePagerState.currSelectIndex.value = goToIndex
                        composePagerState.offsetAnim.snapTo(-goToIndex * composePagerState.mainAxisSize.toFloat())
                        returnPageChangeAnimFlag = pageChangeAnimFlag
                        isNextPage = flag
                    }
                }
            } finally {
                composePagerState.pageChangeAnimFlag = returnPageChangeAnimFlag
            }
        })

    //测量和放置compose元素
    Layout(
        content = {
            contentList.forEach {
                key(it.key) {
                    it.function(it.paramModifier, it.paramScope)
                }
            }
        },
        modifier = modifier
            .scrollable(
                state = scrollableState,
                orientation = orientation,
                enabled = userEnable,
                interactionSource = scrollableInteractionSource,
                flingBehavior = remember<FlingBehavior>(orientation) {
                    object : FlingBehavior {
                        override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
                            val index = composePagerState.currSelectIndex.value
                            if (composePagerState.offsetAnim.value + initialVelocity > -(index * composePagerState.mainAxisSize - composePagerState.mainAxisSize / 2)) {
                                composePagerState.pageChangeAnimFlag = PageChangeAnimFlag.Prev
                            } else if (composePagerState.offsetAnim.value + initialVelocity < -(index * composePagerState.mainAxisSize + composePagerState.mainAxisSize / 2)) {
                                composePagerState.pageChangeAnimFlag = PageChangeAnimFlag.Next
                            } else {
                                composePagerState.pageChangeAnimFlag = PageChangeAnimFlag.Reduction
                            }
                            return 0f//返回剩余的速度
                        }
                    }
                })
            .clipScrollableContainer(orientation)
    ) { measurableList/* 可测量的(子控件) */, constraints/* 约束条件 */ ->
        val selectIndex = composePagerState.currSelectIndex.value
        var width = 0
        var height = 0
        //测量子元素,并算出他们的最大宽度
        val placeableList = measurableList
            .filter {
                //只测量有效的布局
                val key = it.layoutId
                key is Int && abs(key - selectIndex) <= pageCache
            }
            .map {
                val key = it.layoutId as Int
                val placeable = it.measure(constraints)
                width = maxOf(width, placeable.width)
                height = maxOf(height, placeable.height)
                key to placeable
            }

        composePagerState.mainAxisSize =
            if (orientation == Orientation.Horizontal) width else height
        //设置自身大小,并布局子元素
        layout(width, height) {
            val animValue = composePagerState.offsetAnim.value.roundToInt()
            placeableList.forEach { (index, placeable) ->
                val offset = index * composePagerState.mainAxisSize + animValue
                //遍历放置子元素
                if (orientation == Orientation.Horizontal)
                    placeable.placeRelative(
                        x = offset,
                        y = 0
                    )//placeRelative可以适配从右到左布局的放置子元素,place只适用于从左到右的布局
                else
                    placeable.placeRelative(
                        x = 0,
                        y = offset
                    )
            }
        }
    }
}

//初始化ContentList
private fun initContentList(
    composePagerState: ComposePagerState,
    pageCache: Int,
    indexToKey: (index: Int) -> Int,
    contentList: MutableList<ComposePagerContentBean>,
    pageCount: Int,
    content: @Composable (ComposePagerScope.() -> Unit)
) {
    contentList.clear()
    //当前索引
    val selectIndex = composePagerState.currSelectIndex.value
    //key的集合: key to index
    val keyMap = (selectIndex - pageCache).rangeTo(selectIndex + pageCache)
        .associateBy { indexToKey(it) }
    //特殊处理一下banner中数量过少的问题,保证至少有三条
    val keyMapSize = keyMap.size
    if (keyMapSize == 1) {
        val node = keyMap.entries.first()
        repeat(3) {
            val key = node.key
            contentList.add(ComposePagerContentBean(
                key,
                Modifier.layoutId(node.value + it - 2),
                ComposePagerScope(key)
            ) { mModifier, mScope ->
                if (key < 0 || key >= pageCount)
                    Box(modifier = Modifier)
                else {
                    Box(modifier = mModifier) {
                        mScope.content()
                    }
                }
            })
        }
    } else if (keyMapSize == 2) {
        repeat(2) {
            keyMap.forEach { node ->
                val key = node.key
                contentList.add(ComposePagerContentBean(
                    key,
                    Modifier.layoutId((node.value - it * 2)),
                    ComposePagerScope(key)
                ) { mModifier, mScope ->
                    if (key < 0 || key >= pageCount)
                        Box(modifier = Modifier)
                    else {
                        Box(modifier = mModifier) {
                            mScope.content()
                        }
                    }
                })
            }
        }
        contentList.reverse()
    } else {
        //创建或修改缓存
        keyMap.forEach { node ->
            val key = node.key
            contentList.add(ComposePagerContentBean(
                key,
                Modifier.layoutId(node.value),
                ComposePagerScope(key)
            ) { mModifier, mScope ->
                if (key < 0 || key >= pageCount)
                    Box(modifier = Modifier)
                else {
                    Box(modifier = mModifier) {
                        mScope.content()
                    }
                }
            })
        }
    }
}

//通过index确定key,用来保存和复用content
internal val LocalIndexToKey = compositionLocalOf<(index: Int) -> Int> { { it } }

//应该不会有人这样用吧...
@Composable
fun BannerScope.InnerComposePager(
    pageCount: Int,
    modifier: Modifier = Modifier,
    composePagerState: ComposePagerState = rememberComposePagerState(),
    orientation: Orientation = Orientation.Horizontal,
    userEnable: Boolean = true,
    pageCache: Int = 1,
    content: @Composable ComposePagerScope.() -> Unit
) {
    CompositionLocalProvider(LocalIndexToKey provides { it }) {
        ComposePager(
            pageCount = pageCount,
            modifier = modifier,
            composePagerState = composePagerState,
            orientation = orientation,
            userEnable = userEnable,
            pageCache = pageCache,
            content = content,
        )
    }
}