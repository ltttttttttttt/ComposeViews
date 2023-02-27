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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import com.lt.compose_views.util.ComposePosition

/**
 * creator: lt  2022/9/18  lt.dygzs@qq.com
 * effect : [RefreshLayout]的[NestedScrollConnection]
 *          [NestedScrollConnection] of the [RefreshLayout]
 * warning:
 */
internal class RefreshLayoutNestedScrollConnection(
    private val composePosition: ComposePosition,
    private val refreshLayoutState: RefreshLayoutState,
    private val dragEfficiency: Float,
    private val orientationIsHorizontal: Boolean
) : NestedScrollConnection {
    //处理子组件用不完的手势,返回消费的手势
    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        if (source == NestedScrollSource.Drag) {
            when (composePosition) {
                ComposePosition.Start -> {
                    val value = available.x
                    if (value > 0) {
                        //过滤误差值(系统bug?)
                        if (value > 0.01f)
                            refreshLayoutState.offset(value * dragEfficiency)
                        return Offset(value, 0f)
                    }
                }
                ComposePosition.End -> {
                    val value = available.x
                    if (value < 0) {
                        if (value < -0.01f)
                            refreshLayoutState.offset(value * dragEfficiency)
                        return Offset(value, 0f)
                    }
                }
                ComposePosition.Top -> {
                    val value = available.y
                    if (value > 0) {
                        if (value > 0.01f)
                            refreshLayoutState.offset(value * dragEfficiency)
                        return Offset(0f, value)
                    }
                }
                ComposePosition.Bottom -> {
                    val value = available.y
                    if (value < 0) {
                        if (value < -0.01f)
                            refreshLayoutState.offset(value * dragEfficiency)
                        return Offset(0f, value)
                    }
                }
            }
        }
        return Offset.Zero
    }

    //预先处理手势,返回消费的手势
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        //如果是刷新中状态,就拒绝对刷新区域和上下区域滚动
        if (refreshLayoutState.refreshContentState.value == RefreshContentStateEnum.Refreshing) {
            return if (orientationIsHorizontal)
                Offset(available.x, 0f)
            else
                Offset(0f, available.y)
        }
        val refreshOffset = refreshLayoutState.refreshContentOffsetState.value
        if (source == NestedScrollSource.Drag) {
            when (composePosition) {
                ComposePosition.Start -> {
                    if (available.x < 0 && refreshOffset > 0) {
                        //消费的手势
                        var consumptive = available.x
                        if (-available.x > refreshOffset) {
                            consumptive = available.x - refreshOffset
                        }
                        refreshLayoutState.offset(consumptive * dragEfficiency)
                        return Offset(consumptive, 0f)
                    }
                }
                ComposePosition.End -> {
                    if (available.x > 0 && refreshOffset < 0) {
                        //消费的手势
                        var consumptive = available.x
                        if (-available.x > refreshOffset) {
                            consumptive = available.x - refreshOffset
                        }
                        refreshLayoutState.offset(consumptive * dragEfficiency)
                        return Offset(consumptive, 0f)
                    }
                }
                ComposePosition.Top -> {
                    if (available.y < 0 && refreshOffset > 0) {
                        //消费的手势
                        var consumptive = available.y
                        if (-available.y > refreshOffset) {
                            consumptive = available.y - refreshOffset
                        }
                        refreshLayoutState.offset(consumptive * dragEfficiency)
                        return Offset(0f, consumptive)
                    }
                }
                ComposePosition.Bottom -> {
                    if (available.y > 0 && refreshOffset < 0) {
                        //消费的手势
                        var consumptive = available.y
                        if (-available.y < refreshOffset) {
                            consumptive = available.y - refreshOffset
                        }
                        refreshLayoutState.offset(consumptive * dragEfficiency)
                        return Offset(0f, consumptive)
                    }
                }
            }
        }
        return Offset.Zero
    }

    //手势惯性滑动前回调,返回消费的速度,可以当做action_up
    override suspend fun onPreFling(available: Velocity): Velocity {
        //如果是刷新中状态,就拒绝对刷新区域和上下区域滚动
        if (refreshLayoutState.refreshContentState.value == RefreshContentStateEnum.Refreshing) {
            return available
        }
        if (refreshLayoutState.refreshContentOffsetState.value != 0f) {
            refreshLayoutState.offsetHoming()
            return available
        }
        return Velocity.Zero
    }
}

@Composable
internal fun rememberRefreshLayoutNestedScrollConnection(
    composePosition: ComposePosition,
    refreshLayoutState: RefreshLayoutState,
    dragEfficiency: Float,
    orientationIsHorizontal: Boolean
) = remember(composePosition) {
    RefreshLayoutNestedScrollConnection(
        composePosition, refreshLayoutState, dragEfficiency, orientationIsHorizontal
    )
}