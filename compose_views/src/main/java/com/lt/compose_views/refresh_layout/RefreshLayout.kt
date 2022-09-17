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
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import com.lt.compose_views.util.ComposePosition
import kotlin.math.roundToInt

/**
 * creator: lt  2022/6/27  lt.dygzs@qq.com
 * effect : 可以上拉加载和下拉刷新的容器
 * warning:
 * @param refreshContent 刷新布局内容区域
 * @param refreshLayoutState RefreshLayout的状态
 * @param modifier 修饰
 * @param refreshContentThreshold 刷新布局拖动的阈值,拖动超过多少松开才算真的刷新,如果为null,表示为[refreshContent]的宽或高
 * @param composePosition 设置刷新布局的位置,并且间接指定了滑动方向
 * @param contentIsMove content组件是否在刷新时跟着移动,true的效果类似于PullToRefresh,false的效果类似于SwipeRefreshLayout
 * @param dragEfficiency 拖动的'有效率',比如默认是手指拖动20px,只能拖出10px
 * @param isSupportCanNotScrollCompose 是否需要支持无法滚动的组件,为true的话内部会套一层可滚动组件
 * @param userEnable 用户是否可以拖动,等于false时用户拖动无反应,但代码可以修改刷新状态
 * @param content compose内容区域
 */
@Composable
@ExperimentalFoundationApi//todo by lt 待优化和完整的api composePosition
fun RefreshLayout(
    refreshContent: @Composable RefreshLayoutState.() -> Unit,
    refreshLayoutState: RefreshLayoutState,
    modifier: Modifier = Modifier,
    refreshContentThreshold: Dp? = null,
    composePosition: ComposePosition = ComposePosition.Top,
    contentIsMove: Boolean = true,
    dragEfficiency: Float = 0.5f,
    isSupportCanNotScrollCompose: Boolean = false,
    userEnable: Boolean = true,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    //更新状态
    remember(
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
        refreshLayoutState
    }
    val nestedScrollState = remember(composePosition, contentIsMove) {
        // TODO by lt 2022/9/16 20:25 根据方向,优化代码(提出来)
        object : NestedScrollConnection {
            //处理子组件用不完的手势,返回消费的手势
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (source == NestedScrollSource.Drag && available.y > 0) {
                    refreshLayoutState.offset(available.y * dragEfficiency)
                    return Offset(0f, available.y)
                } else {
                    return Offset.Zero
                }
            }

            //预先处理手势,返回消费的手势
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                //如果是刷新中状态,就拒绝对刷新区域和上下区域滚动
                if (refreshLayoutState.refreshContentState.value == RefreshContentStateEnum.Refreshing) {
                    return Offset(0f, available.y)
                }
                val refreshOffset = refreshLayoutState.refreshContentOffsetState.value
                if (source == NestedScrollSource.Drag && available.y < 0 && refreshOffset > 0) {
                    //消费的手势
                    var consumptive = available.y
                    if (-available.y > refreshOffset) {
                        consumptive = available.y - refreshOffset
                    }
                    refreshLayoutState.offset(consumptive * dragEfficiency)
                    return Offset(0f, consumptive)
                } else {
                    return Offset.Zero
                }
            }

            //手势惯性滑动前回调,返回消费的速度
            override suspend fun onPreFling(available: Velocity): Velocity {
                //如果是刷新中状态,就拒绝对刷新区域和上下区域滚动
                if (refreshLayoutState.refreshContentState.value == RefreshContentStateEnum.Refreshing) {
                    return available
                }
                // TODO by lt 2022/9/16 17:50 当作手势抬起事件,并处理content不是可滑动组件的下拉效果
                if (refreshLayoutState.refreshContentOffsetState.value > 0) {
                    refreshLayoutState.setOffsetWithAnim(0f, true)
                    return available
                }
                return Velocity.Zero
            }
        }
    }

    Layout(
        content = {
            if (isSupportCanNotScrollCompose) {
                Box(
                    if (composePosition.orientation == Orientation.Horizontal)
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
        //.clipScrollableContainer(composePosition.orientation) // TODO by lt test 方便测试
    ) { measurables, constraints ->
        val contentPlaceable = measurables[0].measure(constraints.copy(minWidth = 0, minHeight = 0))
        //宽或高不能超过content(根据方向来定)
        val refreshContentPlaceable = measurables[1].measure(
            Constraints(
                maxWidth = if (composePosition.orientation == Orientation.Horizontal) Constraints.Infinity else contentPlaceable.width,
                maxHeight = if (composePosition.orientation == Orientation.Horizontal) contentPlaceable.height else Constraints.Infinity,
            )
        )

        if (refreshContentThreshold == null) {
            refreshLayoutState.refreshContentThresholdState.value =
                if (composePosition.orientation == Orientation.Horizontal) {
                    refreshContentPlaceable.width.toFloat()
                } else {
                    refreshContentPlaceable.height.toFloat()
                }
        }

        layout(contentPlaceable.width, contentPlaceable.height) {
            val offset = refreshLayoutState.refreshContentOffsetState.value.roundToInt()
            // TODO by lt 待修改,根据[composePosition]
            contentPlaceable.placeRelative(0, if (contentIsMove) offset else 0)
            refreshContentPlaceable.placeRelative(0, (-refreshContentPlaceable.height) + offset)
        }
    }
}