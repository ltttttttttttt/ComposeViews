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

package com.lt.compose_views.chain_scrollable_component

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import com.lt.compose_views.util.midOf

/**
 * creator: lt  2022/9/29  lt.dygzs@qq.com
 * effect : 对应[ChainMode.ChainContentFirst]的[NestedScrollConnection]
 * warning:
 */
internal class ChainContentFirstNestedScrollConnection(
    val state: ChainScrollableComponentState,
) : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val delta = if (state.orientationIsHorizontal) available.x else available.y
        val newOffset = state.getScrollPositionValue() + delta
        state.setScrollPosition(midOf(state.minPx, newOffset, state.maxPx))
        return Offset.Zero
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        state.callOnScrollStop()
        return super.onPostFling(consumed, available)
    }
}