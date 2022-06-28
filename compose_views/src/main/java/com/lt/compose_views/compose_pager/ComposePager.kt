package com.lt.compose_views.compose_pager

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import com.lt.compose_views.midOf
import kotlinx.coroutines.CoroutineScope

/**
 * creator: lt  2022/6/25  lt.dygzs@qq.com
 * effect : 类似于xml中的ViewPager
 * warning:
 * [pageCount]一共有多少页
 * [modifier]修饰
 * [composePagerState]ComposePager的状态
 * [orientation]滑动的方向
 * [content]compose内容区域
 */
@Composable
fun ComposePager(
    pageCount: Int,
    modifier: Modifier = Modifier,
    composePagerState: ComposePagerState = rememberComposePagerState(),
    orientation: Orientation = Orientation.Horizontal,
    content: @Composable ComposePagerScope.() -> Unit
) {
    //记录ComposePager的宽高
    var width = remember { 0 }
    var height = remember { 0 }
    //拖动的动画实现
    val offset = remember {
        val anim = Animatable(0f)
        composePagerState.anim = anim
        anim
    }
    //用于配合滑动和动画
    var mOffset by remember {
        mutableStateOf(0f)
    }
    //滑动监听
    val draggableState = rememberDraggableState {
        val maxNumber = if (orientation == Orientation.Horizontal) width else height
        val min = if (composePagerState.currSelectIndex.value + 1 >= pageCount)
            0f else -maxNumber.toFloat()
        val max = if (composePagerState.currSelectIndex.value <= 0)
            0f else maxNumber.toFloat()
        mOffset = midOf(min, mOffset + it, max)
    }
    val currSelectIndex = composePagerState.currSelectIndex.value
    //放置的三个compose元素的scope,分别是0,1,2
    val composePagerScope0 = remember(currSelectIndex) {
        ComposePagerScope(currSelectIndex - 1)
    }
    val composePagerScope1 = remember(currSelectIndex) {
        ComposePagerScope(currSelectIndex)
    }
    val composePagerScope2 = remember(currSelectIndex) {
        ComposePagerScope(currSelectIndex + 1)
    }
    //记录翻页标志位
    var pageChangeAnimFlag by remember {
        mutableStateOf<PageChangeAnimFlag?>(null)
    }

    //处理offset
    LaunchedEffect(key1 = mOffset, block = {
        offset.snapTo(mOffset)
    })
    //处理翻页动画
    LaunchedEffect(key1 = pageChangeAnimFlag, block = {
        val flag = pageChangeAnimFlag ?: return@LaunchedEffect
        try {
            if (orientation == Orientation.Horizontal) {
                when (flag) {
                    is PageChangeAnimFlag.Prev -> {
                        try {
                            offset.animateTo(width.toFloat())
                        } finally {
                            composePagerState.currSelectIndex.value--
                            mOffset = 0f
                        }
                    }
                    is PageChangeAnimFlag.Next -> {
                        try {
                            offset.animateTo(-width.toFloat())
                        } finally {
                            composePagerState.currSelectIndex.value++
                            mOffset = 0f
                        }
                    }
                    is PageChangeAnimFlag.Reduction -> {
                        offset.animateTo(0f)
                    }
                }
            } else {
                when (flag) {
                    is PageChangeAnimFlag.Prev -> {
                        try {
                            offset.animateTo(height.toFloat())
                        } finally {
                            composePagerState.currSelectIndex.value--
                            mOffset = 0f
                        }
                    }
                    is PageChangeAnimFlag.Next -> {
                        try {
                            offset.animateTo(-height.toFloat())
                        } finally {
                            composePagerState.currSelectIndex.value++
                            mOffset = 0f
                        }
                    }
                    is PageChangeAnimFlag.Reduction -> {
                        offset.animateTo(0f)
                    }
                }
            }
        } finally {
            pageChangeAnimFlag = null
        }
    })

    //测量和放置compose元素
    Layout(
        content = {
            if (composePagerScope0.index < 0)
                Box(modifier = Modifier)
            else
                composePagerScope0.content()
            composePagerScope1.content()
            if (composePagerScope2.index >= pageCount)
                Box(modifier = Modifier)
            else
                composePagerScope2.content()
        },
        modifier = modifier
            .draggable(draggableState, orientation, onDragStarted = {
                mOffset = 0f
                composePagerState.onUserDragStarted?.invoke(this, it)
            }, onDragStopped = {
                if (orientation == Orientation.Horizontal) {
                    if (offset.value + it > width / 2) {
                        pageChangeAnimFlag = PageChangeAnimFlag.Prev()
                    } else if (offset.value + it < -width / 2) {
                        pageChangeAnimFlag = PageChangeAnimFlag.Next()
                    } else {
                        pageChangeAnimFlag = PageChangeAnimFlag.Reduction()
                    }
                } else {
                    if (offset.value + it > height / 2) {
                        pageChangeAnimFlag = PageChangeAnimFlag.Prev()
                    } else if (offset.value + it < -height / 2) {
                        pageChangeAnimFlag = PageChangeAnimFlag.Next()
                    } else {
                        pageChangeAnimFlag = PageChangeAnimFlag.Reduction()
                    }
                }
                composePagerState.onUserDragStopped?.invoke(this, it)
            })
    ) { measurables/* 可测量的(子控件) */, constraints/* 约束条件 */ ->
        width = 0
        height = 0
        //测量子元素,并算出他们的最大宽度
        val placeables = measurables.map {
            val placeable = it.measure(constraints)
            width = maxOf(width, placeable.width)
            height = maxOf(height, placeable.height)
            placeable
        }
        //设置自身大小,并布局子元素
        layout(width, height) {
            val animValue = offset.value.toInt()
            placeables.forEachIndexed { index, placeable ->
                //遍历放置子元素
                if (orientation == Orientation.Horizontal)
                    placeable.placeRelative(
                        x = (if (index == 1) 0 else if (index == 0) -width else width) + animValue,
                        y = 0
                    )//placeRelative可以适配从右到左布局的放置子元素,place只适用于从左到右的布局
                else
                    placeable.placeRelative(
                        x = 0,
                        y = (if (index == 1) 0 else if (index == 0) -height else height) + animValue
                    )
            }
        }
    }
}

/**
 * 创建一个[remember]的[ComposePagerState]
 */
@Composable
fun rememberComposePagerState() = remember { ComposePagerState(mutableStateOf(0)) }

/**
 * ComposePager的compose作用域
 * [currSelectIndex]当前ComposePager所在的索引
 * [onUserDragStarted]监听用户开始滑动
 * [onUserDragStopped]监听用户结束滑动
 */
class ComposePagerState(
    val currSelectIndex: MutableState<Int>,
    var onUserDragStarted: (suspend CoroutineScope.(startedPosition: Offset) -> Unit)? = null,
    var onUserDragStopped: (suspend CoroutineScope.(velocity: Float) -> Unit)? = null,
) {
    // TODO by lt 2022/6/27 18:19 增加使用动画切换页数的功能,和普通切换页数的功能
    /**
     * 动画是否执行中
     */
    fun isAnimRunning(): Boolean = anim?.isRunning ?: false

    /**
     * 获取Offset偏移量的state对象
     */
    fun getOffsetState(): State<Float>? = anim?.asState()

    internal var anim: Animatable<Float, *>? = null
}

/**
 * ComposePager的compose作用域
 * [index]当前的ComposePager布局[content]所在的索引
 */
class ComposePagerScope(
    val index: Int,
)

//内部使用的翻页标志位
private sealed class PageChangeAnimFlag {
    //下一页
    class Next : PageChangeAnimFlag()

    //上一页
    class Prev : PageChangeAnimFlag()

    //还原offset
    class Reduction : PageChangeAnimFlag()
}