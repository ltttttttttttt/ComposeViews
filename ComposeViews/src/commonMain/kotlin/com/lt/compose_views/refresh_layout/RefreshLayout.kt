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

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import com.lt.compose_views.util.ComposePosition
import com.lt.compose_views.util.ComposePosition.*
import kotlin.math.roundToInt

/**
 * creator: lt  2022/6/27  lt.dygzs@qq.com
 * effect : 可以任意方向拖动刷新的容器
 *          The refreshed container can be dragged in any direction
 * warning:
 * @param refreshContent 刷新布局内容区域
 *                       Refreshed content area
 * @param refreshLayoutState RefreshLayout的状态,可以调用[rememberRefreshLayoutState]方法创建state并传入一个刷新时触发的回调
 *                           State of the [RefreshLayout]
 * @param modifier 修饰
 * @param refreshContentThreshold 刷新布局拖动的阈值,拖动超过多少松开才算真的刷新,如果为null,表示为[refreshContent]的宽或高
 *                                Refresh threshold for layout dragging
 * @param composePosition 设置刷新布局所在的位置,并且间接指定了滑动方向
 *                        Set where the refreshed layout is located
 * @param contentIsMove content组件是否在刷新时跟着移动,true的效果类似于PullToRefresh,false的效果类似于SwipeRefreshLayout
 *                      Whether the content component moves with it on refresh
 * @param dragEfficiency 拖动的'有效率',比如默认是手指拖动20px,只能拖出10px
 *                       The 'efficiency' of dragging
 * @param isSupportCanNotScrollCompose 是否需要支持无法滚动的组件,为true的话内部会套一层可滚动组件
 *                                     Whether to support non-scrollable components
 * @param userEnable 用户是否可以拖动,等于false时用户拖动无反应,但代码可以修改刷新状态
 *                   Whether the user can drag
 * @param content compose内容区域
 *                Content of compose
 */
@Composable
fun RefreshLayout(
    refreshContent: @Composable RefreshLayoutState.() -> Unit,
    refreshLayoutState: RefreshLayoutState,
    modifier: Modifier = Modifier,
    refreshContentThreshold: Dp? = null,
    composePosition: ComposePosition = Top,
    contentIsMove: Boolean = true,
    dragEfficiency: Float = 0.5f,
    isSupportCanNotScrollCompose: Boolean = false,
    userEnable: Boolean = true,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    //更新状态
    val orientationIsHorizontal = remember(
        refreshLayoutState,
        composePosition,
        refreshContentThreshold,
        coroutineScope,
    ) {
        refreshLayoutState.composePositionState.value = composePosition
        refreshLayoutState.coroutineScope = coroutineScope
        if (refreshContentThreshold != null)
            refreshLayoutState.refreshContentThresholdState.value =
                with(density) { refreshContentThreshold.toPx() }
        composePosition.isHorizontal()
    }
    val nestedScrollState = rememberRefreshLayoutNestedScrollConnection(
        composePosition,
        refreshLayoutState,
        dragEfficiency,
        orientationIsHorizontal
    )

    Layout(
        content = {
            if (isSupportCanNotScrollCompose) {
                Box(
                    if (orientationIsHorizontal)
                        Modifier.horizontalScroll(rememberScrollState())
                    else
                        Modifier.verticalScroll(rememberScrollState())
                ) {
                    content()
                }
            } else {
                content()
            }
            refreshLayoutState.refreshContent()
        },
        modifier = modifier
            .let {
                if (userEnable)
                    it.nestedScroll(nestedScrollState)
                else
                    it
            }
            .clipScrollableContainer(composePosition.orientation)
    ) { measurableList, constraints ->
        val contentPlaceable =
            measurableList[0].measure(constraints.copy(minWidth = 0, minHeight = 0))
        //宽或高不能超过content(根据方向来定)
        val refreshContentPlaceable = measurableList[1].measure(
            Constraints(
                maxWidth = if (orientationIsHorizontal) Constraints.Infinity else contentPlaceable.width,
                maxHeight = if (orientationIsHorizontal) contentPlaceable.height else Constraints.Infinity,
            )
        )
        if (refreshContentThreshold == null && refreshLayoutState.refreshContentThresholdState.value == 0f) {
            refreshLayoutState.refreshContentThresholdState.value =
                if (orientationIsHorizontal) {
                    refreshContentPlaceable.width.toFloat()
                } else {
                    refreshContentPlaceable.height.toFloat()
                }
        }

        layout(contentPlaceable.width, contentPlaceable.height) {
            val offset = refreshLayoutState.refreshContentOffsetState.value.roundToInt()
            when (composePosition) {
                Start -> {
                    contentPlaceable.placeRelative(if (contentIsMove) offset else 0, 0)
                    refreshContentPlaceable.placeRelative(
                        (-refreshContentPlaceable.width) + offset,
                        0
                    )
                }
                End -> {
                    contentPlaceable.placeRelative(if (contentIsMove) offset else 0, 0)
                    refreshContentPlaceable.placeRelative(
                        contentPlaceable.width + offset,
                        0
                    )
                }
                Top -> {
                    contentPlaceable.placeRelative(0, if (contentIsMove) offset else 0)
                    refreshContentPlaceable.placeRelative(
                        0,
                        (-refreshContentPlaceable.height) + offset
                    )
                }
                Bottom -> {
                    contentPlaceable.placeRelative(0, if (contentIsMove) offset else 0)
                    refreshContentPlaceable.placeRelative(
                        0,
                        contentPlaceable.height + offset
                    )
                }
            }
        }
    }
}