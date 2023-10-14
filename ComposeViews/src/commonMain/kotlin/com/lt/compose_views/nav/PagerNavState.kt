package com.lt.compose_views.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.ComposePagerScope
import com.lt.compose_views.compose_pager.ComposePagerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * creator: lt  2023/6/17  lt.dygzs@qq.com
 * effect : [PagerNav]导航的状态
 *          [PagerNav] state of navigation
 * warning:
 * @param navContents 导航的内容
 *                    Navigation Content
 * @param composePagerState [ComposePager]的状态
 *                          State of the [ComposePager]
 */
@Stable
class PagerNavState(
    val navContents: List<NavContent>,
    val composePagerState: ComposePagerState = ComposePagerState(),
) {
    /**
     * 导航到指定页面
     * Navigate to the specified page
     * @param route 要跳转的页面的路由
     *              The route of the page to jump to
     * @param isAnimate 导航是是否有动画
     *                  Whether the navigation is animated
     */
    fun nav(route: String, isAnimate: Boolean = false) {
        val index = navContents.indexOfFirst { it.route == route }
        if (index < 0)
            throw RuntimeException("Route not find: $route")
        if (isAnimate)
            composePagerState.setPageIndexWithAnimate(index)
        else
            composePagerState.setPageIndex(index)
    }

    /**
     * 创建当前路由的Flow
     * Create a Flow for the current route
     */
    fun createCurrRouteFlow(): Flow<String> =
        composePagerState.createCurrSelectIndexFlow()
            .map { navContents[it].route }

    /**
     * 获取当前的路由
     * Get the current route
     */
    fun currRoute(): String = navContents[composePagerState.getCurrSelectIndex()].route
}
