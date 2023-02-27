/*
 * Copyright lt 2023
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

package com.lt.compose_views.refresh_layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.lt.compose_views.refresh_layout.refresh_content.top.PullToRefreshContent

/**
 * creator: lt  2022/9/18  lt.dygzs@qq.com
 * effect : 下拉刷新
 *          Pull down to refresh
 * warning:
 * @param refreshLayoutState RefreshLayout的状态
 *                           State of the [RefreshLayout]
 * @param modifier 修饰
 * @param refreshContent 刷新布局内容区域
 *                       Refreshed content area
 * @param content compose内容区域
 *                       Content of compose
 */
@Composable
fun PullToRefresh(
    refreshLayoutState: RefreshLayoutState,
    modifier: Modifier = Modifier,
    refreshContent: @Composable RefreshLayoutState.() -> Unit = remember {
        { PullToRefreshContent() }
    },
    content: @Composable () -> Unit,
) {
    RefreshLayout(
        refreshContent = refreshContent,
        refreshLayoutState = refreshLayoutState,
        modifier = modifier,
        content = content
    )
}