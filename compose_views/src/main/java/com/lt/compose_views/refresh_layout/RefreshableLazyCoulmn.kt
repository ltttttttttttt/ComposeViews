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

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lt.compose_views.refresh_layout.refresh_content.bottom.LoadMoreRefreshContent
import com.lt.compose_views.refresh_layout.refresh_content.top.PullToRefreshContent
import com.lt.compose_views.util.ComposePosition

/**
 * creator: lt  2022/9/18  lt.dygzs@qq.com
 * effect : 下拉刷新+上拉加载的LazyColumn
 * warning:
 * @param topRefreshLayoutState top的刷新布局的state,可以调用[rememberRefreshLayoutState]方法创建state并传入一个刷新时触发的回调
 * @param bottomRefreshLayoutState bottom的刷新布局的state,可以调用[rememberRefreshLayoutState]方法创建state并传入一个刷新时触发的回调
 * @param modifier 修饰
 * @param topRefreshContent top的刷新布局的content,有默认样式,可以传入lambda自定义
 * @param bottomIsLoadFinish bottom刷新布局是否刷新完成
 * @param bottomRefreshContent bottom的刷新布局的content,有默认样式,可以传入lambda自定义
 * @param lazyColumnState lazyColumn的State
 * @param contentPadding 内部边距设置,可以设置为整体边距或条目间的间距
 * @param reverseLayout 反转滚动和布局的方向
 * @param verticalArrangement 竖向对其方式
 * @param horizontalAlignment 横向对其方式
 * @param flingBehavior fling
 * @param userScrollEnabled 用户是否可以滚动
 * @param content lazyColumn的内容
 */
@Composable
fun RefreshableLazyColumn(
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
    lazyColumnState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    content: LazyListScope.() -> Unit
) {
    RefreshLayout(
        refreshContent = topRefreshContent,
        refreshLayoutState = topRefreshLayoutState
    ) {
        RefreshLayout(
            refreshContent = bottomRefreshContent,
            refreshLayoutState = bottomRefreshLayoutState,
            composePosition = ComposePosition.Bottom
        ) {
            LazyColumn(
                modifier = modifier,
                state = lazyColumnState,
                contentPadding = contentPadding,
                reverseLayout = reverseLayout,
                verticalArrangement = verticalArrangement,
                horizontalAlignment = horizontalAlignment,
                flingBehavior = flingBehavior,
                userScrollEnabled = userScrollEnabled,
                content = content
            )
        }
    }
}