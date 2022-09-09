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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import com.lt.compose_views.util.ComposePosition

/**
 * creator: lt  2022/6/27  lt.dygzs@qq.com
 * effect : 可以上拉加载和下拉刷新的容器
 * warning:
 * [refreshContent]刷新布局内容区域
 * [refreshLayoutState]RefreshLayout的状态
 * [modifier]修饰
 * [refreshContentThreshold]刷新布局拖动的阈值,拖动超过多少松开才算真的刷新,如果为null,表示为[refreshContent]的宽或高
 * [composePosition]设置刷新布局的位置,并且间接指定了滑动方向
 * [childIsMove]内部子组件是否在刷新时跟着移动,true的效果类似于PullToRefresh,false的效果类似于SwipeRefreshLayout
 * [enable]是否可用
 * [content]compose内容区域
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun RefreshLayout(
    refreshContent: @Composable RefreshLayoutState.() -> Unit,
    refreshLayoutState: RefreshLayoutState,
    modifier: Modifier = Modifier,
    refreshContentThreshold: Dp? = null,
    composePosition: ComposePosition = ComposePosition.Top,
    childIsMove: Boolean = true,
    enable: Boolean = true,
    content: @Composable () -> Unit,
) {
    // TODO by lt 待实现
    val density = LocalDensity.current
    //更新状态
    remember(key1 = refreshLayoutState, key2 = composePosition, key3 = refreshContentThreshold) {
        refreshLayoutState.composePositionState.value = composePosition
        if (refreshContentThreshold != null)
            refreshLayoutState.refreshContentThresholdState.value =
                with(density) { refreshContentThreshold.toPx() }
        refreshLayoutState
    }

    Layout(
        content = {
            refreshLayoutState.refreshContent()
            content()
        },
        modifier = modifier
            // TODO by lt 待实现
            .nestedScroll(rememberNestedScrollInteropConnection())
            .clipScrollableContainer(composePosition.orientation)
    ) { measurables, constraints ->
        val refreshContentPlaceable = measurables[0].measure(Constraints())
        val contentPlaceable = measurables[1].measure(constraints)

        if (refreshContentThreshold == null) {
            refreshLayoutState.refreshContentThresholdState.value =
                if (composePosition.orientation == Orientation.Horizontal) {
                    refreshContentPlaceable.width.toFloat()
                } else {
                    refreshContentPlaceable.height.toFloat()
                }
        }

        layout(contentPlaceable.width, contentPlaceable.height) {
            // TODO by lt 待修改
            refreshContentPlaceable.placeRelative(0, -refreshContentPlaceable.height)
            contentPlaceable.placeRelative(0, 0)
        }
    }
}