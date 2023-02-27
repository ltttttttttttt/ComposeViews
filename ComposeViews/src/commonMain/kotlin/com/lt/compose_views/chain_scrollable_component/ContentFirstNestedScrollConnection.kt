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
import com.lt.compose_views.util.midOf

/**
 * creator: lt  2022/9/29  lt.dygzs@qq.com
 * effect : 对应[ChainMode.ContentFirst]的[NestedScrollConnection]
 * warning:
 */
internal class ContentFirstNestedScrollConnection(
    val state: ChainScrollableComponentState,
) : NestedScrollConnection {

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        // TODO by lt 2022/9/30 处理抬起事件,处理 ContentFirst缺少数据应对向上滑动收起
        if (true)
            throw RuntimeException("该模式待完善,请使用[ChainMode.ChainContentFirst]")
        val delta = if (state.orientationIsHorizontal) available.x else available.y
        if (delta < 0) {
            val newOffset = state.getScrollPositionValue() + delta
            //Log.e("lllttt", "pre \t $delta\t ${state.minPx}\t ${state.maxPx}\t $newOffset")
            state.setScrollPosition(midOf(state.minPx, newOffset, state.maxPx))
        } else {
            val newOffset = state.getScrollPositionValue() + delta
            //Log.e("lllttt", "pre2 \t $delta\t ${state.minPx}\t ${state.maxPx}\t $newOffset")
            if (newOffset >= state.maxPx)
                state.setScrollPosition(midOf(state.minPx, newOffset, state.maxPx))
        }
        return Offset.Zero
    }
}