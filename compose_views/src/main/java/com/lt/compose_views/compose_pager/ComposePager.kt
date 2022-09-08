/*
 * Copyright lt 2022
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

import android.util.Log
import androidx.annotation.IntRange
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import com.lt.compose_views.banner.BannerScope
import com.lt.compose_views.util.clipScrollableContainer
import com.lt.compose_views.util.midOf
import java.util.*
import kotlin.math.abs

/**
 * creator: lt  2022/6/25  lt.dygzs@qq.com
 * effect : 类似于xml中的ViewPager
 * warning:
 * [pageCount]一共有多少页
 * [modifier]修饰
 * [composePagerState]ComposePager的状态
 * [orientation]滑动的方向
 * [userEnable]用户是否可以滑动,等于false时用户滑动无反应,但代码可以执行翻页
 * [pageCache]左右两边的页面缓存,默认左右各缓存1页,但不能少于1页(不宜过大)
 * [content]compose内容区域
 */
@Composable
fun ComposePager(
    pageCount: Int,
    modifier: Modifier = Modifier,
    composePagerState: ComposePagerState = rememberComposePagerState(),
    orientation: Orientation = Orientation.Horizontal,
    userEnable: Boolean = true,
    @IntRange(from = 1) pageCache: Int = 1,
    content: @Composable ComposePagerScope.() -> Unit
) {
    // TODO by lt 2022/9/6 22:28 pager闪动问题 ,   测一下pageCache  []改成@par
    //key和content的缓存位置
    val contentMap by remember(pageCount) {
        mutableStateOf(TreeMap<Int, ComposePagerContentBean>())
    }
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

    val indexToKey = LocalIndexToKey.current
    //放置的compose元素的content
    remember(
        key1 = pageCount,
        key2 = composePagerState.currSelectIndex.value,
        key3 = pageCache,
    ) {
        //当前索引
        val selectIndex = composePagerState.currSelectIndex.value
        //key的集合: key to index
        val keyMap = (selectIndex - pageCache).rangeTo(selectIndex + pageCache)
            .associateBy { indexToKey(it) }
        Log.e("lllttt", "keyMap= : $keyMap")
        //清除不必要的缓存
        val keyIterator = contentMap.keys.iterator()
        while (keyIterator.hasNext()) {
            val key = keyIterator.next()
            if (!keyMap.containsKey(key)) {
                keyIterator.remove()
            }
        }
        //创建或修改缓存
        keyMap.forEach { node ->
            val key = node.key
            val contentBean = contentMap[key]
            Log.e("lllttt", ".ComposePager 99 : $contentBean $key")
            if (contentBean != null) {
                contentMap[key] = contentBean.copy(paramModifier = Modifier.layoutId(node.value))
            } else {
                contentMap[key] = ComposePagerContentBean(
                    Modifier.layoutId(node.value),
                    ComposePagerScope(key)
                ) { mModifier, mScope ->
                    Log.e("lllttt", "key= : $key  ${node.value}  pageCount=$pageCount")
                    if (key < 0 || key >= pageCount)
                        Box(modifier = Modifier)
                    else {
                        Box(modifier = mModifier) {
                            mScope.content()
                        }
                    }
                }
            }
        }
        contentMap.forEach {
            Log.e("lllttt", "contentMap= : ${it.key} ${it.value}")
        }
        contentMap
    }
    //滑动监听
    val draggableState = rememberDraggableState {
        //停止之前的动画
        composePagerState.pageChangeAnimFlag = null
        val min = if (composePagerState.currSelectIndex.value + 1 >= pageCount)
            0f else -composePagerState.mainAxisSize.toFloat()
        val max = if (composePagerState.currSelectIndex.value <= 0)
            0f else composePagerState.mainAxisSize.toFloat()
        composePagerState.mOffset = midOf(min, (composePagerState.mOffset ?: 0f) + it, max)
    }

    //处理offset
    LaunchedEffect(
        key1 = composePagerState.mOffset,
        key2 = orientation,
        block = {
            val offset = composePagerState.mOffset ?: return@LaunchedEffect
            composePagerState.offsetAnim.snapTo(
                offset - composePagerState.currSelectIndex.value * composePagerState.mainAxisSize
            )
        })
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
            try {
                val index = composePagerState.currSelectIndex.value
                when (flag) {
                    PageChangeAnimFlag.Prev -> {
                        if (index <= 0)
                            return@LaunchedEffect
                        composePagerState.mOffset = null
                        try {
                            composePagerState.offsetAnim.animateTo(-(index - 1) * composePagerState.mainAxisSize.toFloat())
                        } finally {
                            composePagerState.currSelectIndex.value = index - 1
                            composePagerState.mOffset = 0f
                        }
                    }
                    PageChangeAnimFlag.Next -> {
                        if (index + 1 >= pageCount)
                            return@LaunchedEffect
                        composePagerState.mOffset = null
                        try {
                            composePagerState.offsetAnim.animateTo(-(index + 1) * composePagerState.mainAxisSize.toFloat())
                        } finally {
                            composePagerState.currSelectIndex.value = index + 1
                            composePagerState.mOffset = 0f
                        }
                    }
                    PageChangeAnimFlag.Reduction -> {
                        composePagerState.offsetAnim.animateTo(-index * composePagerState.mainAxisSize.toFloat())
                    }
                    is PageChangeAnimFlag.GoToPageNotAnim -> {
                        composePagerState.currSelectIndex.value = flag.index
                        composePagerState.offsetAnim.snapTo(-flag.index * composePagerState.mainAxisSize.toFloat())
                    }
                }
            } finally {
                composePagerState.pageChangeAnimFlag = null
            }
        })

    //测量和放置compose元素
    Layout(
        content = {

            Log.e("lllttt", "content 99 : ${contentMap.keys}")
            contentMap.values.forEach { it.function(it.paramModifier, it.paramScope) }
        },
        modifier = modifier
            .draggable(draggableState, orientation, enabled = userEnable, onDragStarted = {
                composePagerState.mOffset = 0f
                composePagerState.onUserDragStarted?.invoke(this, it)
            }, onDragStopped = {
                val index = composePagerState.currSelectIndex.value
                if (composePagerState.offsetAnim.value + it > -(index * composePagerState.mainAxisSize - composePagerState.mainAxisSize / 2)) {
                    composePagerState.pageChangeAnimFlag = PageChangeAnimFlag.Prev
                } else if (composePagerState.offsetAnim.value + it < -(index * composePagerState.mainAxisSize + composePagerState.mainAxisSize / 2)) {
                    composePagerState.pageChangeAnimFlag = PageChangeAnimFlag.Next
                } else {
                    composePagerState.pageChangeAnimFlag = PageChangeAnimFlag.Reduction
                }
                composePagerState.onUserDragStopped?.invoke(this, it)
            })
            .clipScrollableContainer(orientation == Orientation.Vertical)
    ) { measurables/* 可测量的(子控件) */, constraints/* 约束条件 */ ->
        val selectIndex = composePagerState.currSelectIndex.value
        var width = 0
        var height = 0
        //测量子元素,并算出他们的最大宽度
        val placeableMap = measurables
            .filter {
                //只测量有效的布局,并且是 -1..1
                val key = it.layoutId
                key is Int && abs(key - selectIndex) <= 1
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
            val animValue = composePagerState.offsetAnim.value.toInt()
            placeableMap.forEach { (index, placeable) ->
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
    @IntRange(from = 1) pageCache: Int = 1,
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