/*
 * Copyright lt 2022
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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.lt.compose_views.refresh_layout.refresh_content.bottom.LoadMoreRefreshContent
import com.lt.compose_views.refresh_layout.refresh_content.top.PullToRefreshContent
import com.lt.compose_views.util.ComposePosition

/**
 * creator: lt  2022/9/18  lt.dygzs@qq.com
 * effect : 下拉刷新+上拉加载,如果内部不支持上下滑动的话,则无法使用(可以给modifier加上[verticalScroll]修饰)
 * warning:
 * @param topRefreshLayoutState top的刷新布局的state,可以调用[rememberRefreshLayoutState]方法创建state并传入一个刷新时触发的回调
 * @param bottomRefreshLayoutState bottom的刷新布局的state,可以调用[rememberRefreshLayoutState]方法创建state并传入一个刷新时触发的回调
 * @param modifier 修饰
 * @param topRefreshContent top的刷新布局的content,有默认样式,可以传入lambda自定义
 * @param bottomIsLoadFinish bottom刷新布局是否刷新完成
 * @param bottomRefreshContent bottom的刷新布局的content,有默认样式,可以传入lambda自定义
 * @param content 内容
 */
@Composable
fun VerticalRefreshableLayout(
    topRefreshLayoutState: RefreshLayoutState,
    bottomRefreshLayoutState: RefreshLayoutState,
    modifier: Modifier = Modifier,
    topRefreshContent: @Composable RefreshLayoutState.() -> Unit = remember {
        { PullToRefreshContent() }
    },
    bottomIsLoadFinish: Boolean = false,
    bottomRefreshContent: @Composable RefreshLayoutState.() -> Unit = remember(bottomIsLoadFinish) {
        { LoadMoreRefreshContent(bottomIsLoadFinish) }
    },
    content: @Composable () -> Unit
) {
    RefreshLayout(
        modifier = modifier,
        refreshContent = topRefreshContent,
        refreshLayoutState = topRefreshLayoutState
    ) {
        RefreshLayout(
            modifier = Modifier.fillMaxSize(),
            refreshContent = bottomRefreshContent,
            refreshLayoutState = bottomRefreshLayoutState,
            composePosition = ComposePosition.Bottom,
            content = content
        )
    }
}

