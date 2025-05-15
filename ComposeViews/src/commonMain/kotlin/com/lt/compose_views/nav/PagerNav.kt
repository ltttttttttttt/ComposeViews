package com.lt.compose_views.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lt.compose_views.compose_pager.ComposePager

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
 * @param userEnable 用户是否可以滑动,等于false时用户滑动无反应,但代码可以执行翻页
 *                   Whether the user can scroll
 */
@Composable
fun PagerNav(
    state: PagerNavState,
    modifier: Modifier = Modifier,
    pageCache: Int = state.navContents.size,
    userEnable: Boolean = false,
) {
    ComposePager(
        state.navContents.size,
        modifier,
        state.composePagerState,
        userEnable = userEnable,
        pageCache = pageCache,
        pagerKey = { state.navContents[it].route },
    ) {
        state.navContents[index].Content(this)
    }
}

