package com.lt.compose_views.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.ComposePagerScope
import com.lt.compose_views.compose_pager.ComposePagerState
import com.lt.compose_views.util.DragInteractionSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * creator: lt  2023/6/17  lt.dygzs@qq.com
 * effect : 以Pager为基础的导航
 *          相对于jetpack的导航:
 *          1.使用更简单,功能更单一
 *          2.可保存每个页面的所有状态
 *          3.无动画
 *
 *          Pager-based navigation
 *          Navigation relative to jetpack:
 *          1.Easier to use and more single function
 *          2.All state of each page can be saved
 *          3.no animation
 * warning:
 * @param state PagerNav的状态
 *              State of PagerNav
 * @param modifier 修饰
 * @param pageCache 左右两边的页面缓存,默认左右各缓存1页,但不能少于1页(不宜过大)
 *                  The number of pagers cached on the left and right sides
 */
@Composable
fun PagerNav(
    state: PagerNavState,
    modifier: Modifier = Modifier,
    pageCache: Int = state.routeAndContents.size,
) {
    ComposePager(
        state.routeAndContents.size,
        modifier,
        state.composePagerState,
        userEnable = false,
        pageCache = pageCache,
    ) {
        state.routeAndContents[index].second(this)
    }
}

