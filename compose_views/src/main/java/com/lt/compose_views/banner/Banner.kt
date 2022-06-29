package com.lt.compose_views.banner

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.ComposePagerScope
import com.lt.compose_views.compose_pager.ComposePagerState
import com.lt.compose_views.compose_pager.rememberComposePagerState
import kotlinx.coroutines.delay

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
    content: @Composable ComposePagerScope.() -> Unit
) {
    //是否正在滚动倒计时中
    val scrolling by remember(autoScroll) {
        val scrolling = mutableStateOf(autoScroll)
        composePagerState.onUserDragStarted = {
            scrolling.value = false
        }
        composePagerState.onUserDragStopped = {
            scrolling.value = autoScroll
        }
        scrolling
    }
    //初始页数为100n,总页数为一百万n
    remember {
        composePagerState.setPageIndex(pageCount * 100)
    }

    if (scrolling)
        LaunchedEffect(key1 = autoScrollTime, block = {
            while (true) {
                delay(autoScrollTime)
                composePagerState.setPageIndexWithAnim(composePagerState.currSelectIndex.value + 1)
            }
        })

    ComposePager(
        pageCount = minOf(pageCount * 1000000, Int.MAX_VALUE),
        modifier = modifier,
        composePagerState = composePagerState,
        orientation = orientation,
    ) {
        content(ComposePagerScope(index % pageCount))
    }
}