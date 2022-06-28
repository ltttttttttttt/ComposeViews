package com.lt.compose_views.banner

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.ComposePagerScope
import com.lt.compose_views.compose_pager.ComposePagerState
import com.lt.compose_views.compose_pager.rememberComposePagerState

/**
 * creator: lt  2022/6/25  lt.dygzs@qq.com
 * effect : 可以自动循环轮播的ComposePager
 * warning:
 * [pageCount]一共有多少页
 * [modifier]修饰
 * [composePagerState]ComposePager的状态
 * [orientation]滑动的方向
 * [autoScroll]是否自动滚动
 * [autoScrollTime]自动滚动间隔时间
 * [scrollAnimTime]自动滚动的动画时间
 * [content]compose内容区域
 */
@Composable
fun Banner(
    pageCount: Int,
    modifier: Modifier = Modifier,
    composePagerState: ComposePagerState = rememberComposePagerState(),
    orientation: Orientation = Orientation.Horizontal,
    autoScroll: Boolean = true,
    autoScrollTime: Long = 3000,
    scrollAnimTime: Long = 500,
    content: @Composable ComposePagerScope.() -> Unit
) {
    // TODO by lt 2022/6/27 18:17 待完善
    val a=remember {
        //init
        composePagerState.onUserDragStarted = {

        }
        composePagerState.onUserDragStopped = {

        }
        0
    }
    if (autoScroll)
        LaunchedEffect(key1 = autoScrollTime, block = {

        })
    ComposePager(
        pageCount = pageCount,
        modifier = modifier,
        composePagerState = composePagerState,
        orientation = orientation,
        content = content,
    )
}