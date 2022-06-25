package com.lt.compose_views.compose_pager

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

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
    Box(modifier) {
        ComposePagerScope(this, composePagerState.currSelectIndex.value).content()
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
    val boxScope: BoxScope,
    val index: Int,
) : BoxScope by boxScope {
}