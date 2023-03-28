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

package com.lt.compose_views.chain_scrollable_component.swipe_to_dismiss

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import com.lt.compose_views.chain_scrollable_component.ChainScrollableComponent
import com.lt.compose_views.chain_scrollable_component.ChainScrollableComponentState
import com.lt.compose_views.util.ComposePosition
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * creator: lt  2022/10/2  lt.dygzs@qq.com
 * effect : 滑动删除控件
 *          Swipe to delete controls
 * warning:
 * @param minScrollPosition 最小滚动位置(距离指定方向的顶点)
 *                          Minimum scroll position
 * @param maxScrollPosition 最大滚动位置(距离指定方向的顶点)
 *                          Maximum scroll position
 * @param backgroundContent 等待拖出的compose内容区域
 *                          Content of background
 * @param modifier 修饰
 * @param contentIsMove compose内容区域是否跟着移动
 *                      Does content follow
 * @param content compose内容区域,需要内容是横向可滚动的,并且需要自行给内容设置相应方向的PaddingValues或padding
 *                Content of compose
 */
@ExperimentalFoundationApi
@Composable
fun SwipeToDismiss(
    minScrollPosition: Dp,
    maxScrollPosition: Dp,
    backgroundContent: @Composable RowScope.(state: ChainScrollableComponentState) -> Unit,
    modifier: Modifier = Modifier,
    contentIsMove: Boolean = true,
    content: @Composable RowScope.(state: ChainScrollableComponentState) -> Unit,
) {
    val scrollState = rememberScrollState()
    ChainScrollableComponent(
        minScrollPosition = minScrollPosition,
        maxScrollPosition = maxScrollPosition,
        chainContent = { state ->
            Row(modifier = Modifier
                .fillMaxHeight()
                .width(maxScrollPosition)
                .offset {
                    IntOffset(
                        state
                            .getScrollPositionValue()
                            .roundToInt(),
                        0
                    )
                }) {
                backgroundContent(state)
            }
        },
        modifier = modifier.height(IntrinsicSize.Min),
        onScrollStop = scrollStop(scrollState),
        composePosition = ComposePosition.End,
        content = { state ->
            Row(
                modifier = Modifier
                    .matchParentSize()
                    .let {
                        if (contentIsMove) {
                            it.offset {
                                IntOffset(
                                    (state.getScrollPositionValue() - state.maxPx).roundToInt(),
                                    0
                                )
                            }
                        } else
                            it
                    }
                    .horizontalScroll(scrollState)
            ) {
                content(state)
            }
        }
    )
}

//停止拖动时,使appbar归位
private fun scrollStop(scrollState: ScrollState): (ChainScrollableComponentState) -> Unit =
    function@{ state ->
        val percentage = state.getScrollPositionPercentage()
        if (percentage == 1f || percentage == 0f)
            return@function
        state.coroutineScope.launch {
            val startPositionValue = state.getScrollPositionValue()
            if (percentage > 0.5f) {
                state.setScrollPositionWithAnimate(state.maxPx)
                scrollState.animateScrollBy(startPositionValue - state.minPx)
            } else {
                state.setScrollPositionWithAnimate(0f)
                scrollState.animateScrollBy(startPositionValue)
            }
        }
    }