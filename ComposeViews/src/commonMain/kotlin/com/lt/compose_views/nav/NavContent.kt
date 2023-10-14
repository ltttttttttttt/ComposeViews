package com.lt.compose_views.nav

import androidx.compose.runtime.Composable
import com.lt.compose_views.compose_pager.ComposePagerScope

/**
 * creator: lt  2023/10/14  lt.dygzs@qq.com
 * effect : [PagerNav]的Compose内容
 *          [PagerNav]'s Compose content
 * warning:
 */
interface NavContent {
    /**
     * 导航的路由
     * Navigation Routing
     */
    val route: String

    /**
     * 导航的内容
     * NavigationContent
     */
    @Composable
    fun Content(scope: ComposePagerScope)
}