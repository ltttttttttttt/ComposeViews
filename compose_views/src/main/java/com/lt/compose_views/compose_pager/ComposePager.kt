package com.lt.compose_views.compose_pager

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.IntOffset

/**
 * creator: lt  2022/6/25  lt.dygzs@qq.com
 * effect : 类似于xml中的ViewPager
 * warning:
 * [modifier]修饰
 * [composePagerState]ComposePager的状态
 * [orientation]滑动的方向,默认横向
 * [content]compose内容区域
 */
@Composable
fun ComposePager(
    modifier: Modifier = Modifier,
    composePagerState: ComposePagerState = rememberComposePagerState(),
    orientation: Orientation = Orientation.Horizontal,
    content: @Composable ComposePagerScope.() -> Unit
) {
    // TODO by lt 2022/6/25 16:56 待完善
    var offset by remember { mutableStateOf(0f) }
    val scrollState = rememberScrollableState { delta ->
        offset += delta
        delta
    }
    //最后的lambda是测量和放置规则
    Layout(content = {
        ComposePagerScope(composePagerState.currSelectIndex.value - 1).content()
        ComposePagerScope(composePagerState.currSelectIndex.value).content()
        ComposePagerScope(composePagerState.currSelectIndex.value + 1).content()
    }, modifier = modifier
        .scrollable(scrollState, orientation)
        .offset {
            if (orientation == Orientation.Horizontal) {
                IntOffset(offset.toInt(), 0)
            } else {
                IntOffset(0, offset.toInt())
            }
        }) { measurables/* 可测量的(子控件) */, constraints/* 约束条件 */ ->
        var width = 0
        var height = 0
        //测量子元素,并算出他们的最大宽度
        val placeables = measurables.map {
            val placeable = it.measure(constraints)
            width = maxOf(width, placeable.width)
            height = maxOf(height, placeable.height)
            placeable
        }
        //设置自身大小,并布局子元素
        layout(width, height) {
            placeables.forEachIndexed { index, placeable ->
                //遍历放置子元素
                if (orientation == Orientation.Horizontal)
                    placeable.placeRelative(
                        x = if (index == 1) 0 else if (index == 0) -width else width,
                        y = 0
                    )//placeRelative可以适配从右到左布局的放置子元素,place只适用于从左到右的布局
                else
                    placeable.placeRelative(
                        x = 0,
                        y = if (index == 1) 0 else if (index == 0) -height else height
                    )
            }
        }
    }
}

@Composable
fun rememberComposePagerState() = remember { ComposePagerState(mutableStateOf(0)) }

/**
 * ComposePager的compose作用域
 * [currSelectIndex]当前ComposePager所在的索引
 */
class ComposePagerState(
    val currSelectIndex: MutableState<Int>,
    //isAnim
    //offset
) {

}

/**
 * ComposePager的compose作用域
 * [index]当前的ComposePager布局[content]所在的索引
 */
class ComposePagerScope(
    val index: Int,
)