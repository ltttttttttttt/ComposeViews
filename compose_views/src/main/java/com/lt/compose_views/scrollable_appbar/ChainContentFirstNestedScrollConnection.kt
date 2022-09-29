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

package com.lt.compose_views.scrollable_appbar

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import com.lt.compose_views.util.ComposePosition

/**
 * creator: lt  2022/9/29  lt.dygzs@qq.com
 * effect : 对应[ChainMode.ChainContentFirst]的[NestedScrollConnection]
 * warning:
 */
internal class ChainContentFirstNestedScrollConnection(
    val state: ChainScrollableComponentState,
    composePosition: ComposePosition,
) : NestedScrollConnection {
    private val orientationIsHorizontal = composePosition.isHorizontal()

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        val offset = if (orientationIsHorizontal) consumed.x else consumed.y
        val position = state.getScrollPositionValue()
        if (offset > 0 && position < state.maxPx) {
            //如果可以向max位置滑动
            return if (orientationIsHorizontal) consumed.copy(y = 0f) else consumed.copy(x = 0f)
        } else if (offset < 0 && position > state.minPx) {
            //如果可以向min位置滑动
            return if (orientationIsHorizontal) consumed.copy(y = 0f) else consumed.copy(x = 0f)
        }
        return Velocity.Zero
    }

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        //还是fling的值有问题
        if (source == NestedScrollSource.Fling)
            return Offset.Zero
        //Log.e("lllttt", "666 ${available.y} $source")
        val offset = if (orientationIsHorizontal) available.x else available.y
        val position = state.getScrollPositionValue()
        val diff = if (offset > 0 && position < state.maxPx) {
            //如果可以向max位置滑动
            minOf(state.maxPx - position, offset)
        } else if (offset < 0 && position > state.minPx) {
            //如果可以向min位置滑动
            maxOf(state.minPx - position, offset)
        } else {
            return Offset.Zero
        }
        if (source == NestedScrollSource.Fling) {
            state.setScrollPositionWithAnimate(position + diff)
        } else {
            state.setScrollPosition(position + diff)
        }
        Log.e("lllttt", "666 $position ${position + diff} $diff $source")
        return Offset(
            if (orientationIsHorizontal) diff else 0f,
            if (orientationIsHorizontal) 0f else diff
        )
    }
}