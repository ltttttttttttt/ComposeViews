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

package com.lt.compose_views.chain_scrollable_component.mode

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import com.lt.compose_views.chain_scrollable_component.ChainScrollableComponentState
import com.lt.compose_views.util.ComposePosition
import com.lt.compose_views.util.midOf

/**
 * creator: lt  2022/9/29  lt.dygzs@qq.com
 * effect : 对应[ChainMode.ContentFirst]的[NestedScrollConnection]
 * warning:
 */
internal class ContentFirstNestedScrollConnection(
    val state: ChainScrollableComponentState,
) : NestedScrollConnection {
    private var scrollSum = 0f

    init {
        if (state.composePosition == ComposePosition.End || state.composePosition == ComposePosition.Bottom)
            throw IllegalStateException("[ChainMode.ContentFirst] not support [ComposePosition.Bottom] and [ComposePosition.End]")
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        val delta = if (state.orientationIsHorizontal) available.x else available.y
        //content先滚动完,底部跟着滚动
        if (delta < 0f) {
            val newOffset = state.getScrollPositionValue() + delta
            state.setScrollPosition(midOf(state.minPx, newOffset, state.maxPx))
        }
        val delta2 = if (state.orientationIsHorizontal) consumed.x else consumed.y
        scrollSum += delta2
        //顶部计算如果剩余位置位于max和min之间,则跟着滚动
        if (delta2 > 0f) {
            if (scrollSum < state.maxPx && scrollSum > state.minPx) {
                val newOffset = state.getScrollPositionValue() + delta2
                state.setScrollPosition(midOf(state.minPx, newOffset, state.maxPx))
            }
        }
        return Offset.Zero
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        val delta = if (state.orientationIsHorizontal) available.x else available.y
        if (state.callOnScrollStop(delta)) return super.onPostFling(consumed, available)
        val newOffset = state.getScrollPositionValue() + delta
        if (delta < 0f) {
            state.animateToScrollPosition(midOf(state.minPx, newOffset, state.maxPx))
        }
        return super.onPostFling(consumed, available)
    }
}