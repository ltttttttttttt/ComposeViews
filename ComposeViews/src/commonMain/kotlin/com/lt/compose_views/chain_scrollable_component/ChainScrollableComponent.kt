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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.lt.compose_views.util.ComposePosition

/**
 * creator: lt  2022/9/28  lt.dygzs@qq.com
 * effect : 链式(联动)滚动组件
 *          Chain scrollable component
 * warning:
 * @param minScrollPosition 最小滚动位置(距离指定方向的顶点)
 *                          Minimum scroll position
 * @param maxScrollPosition 最大滚动位置(距离指定方向的顶点)
 *                          Maximum scroll position
 * @param chainContent 链式(联动)滚动的compose组件,scrollOffset: 滚动位置(位于最小和最大之间)
 *                     Content of chain
 * @param modifier 修饰
 * @param onScrollStop 停止滚动时回调
 *                     Callback of scroll stop event
 * @param composePosition 设置bar布局所在的位置,并且间接指定了滑动方向
 *                        Set the position of the top bar layout
 * @param chainMode 联动方式
 *                  Chain mode
 * @param content compose内容区域,需要内容是在相应方向可滚动的,并且需要自行给内容设置相应方向的PaddingValues或padding
 *                Content of compose
 */
@ExperimentalFoundationApi
@Composable
fun ChainScrollableComponent(
    minScrollPosition: Dp,
    maxScrollPosition: Dp,
    chainContent: @Composable (state: ChainScrollableComponentState) -> Unit,
    modifier: Modifier = Modifier,
    onScrollStop: ((state: ChainScrollableComponentState) -> Unit)? = null,
    composePosition: ComposePosition = ComposePosition.Top,
    chainMode: ChainMode = ChainMode.ChainContentFirst,
    content: @Composable BoxScope.(state: ChainScrollableComponentState) -> Unit,
) {
    val density = LocalDensity.current
    val minPx = remember(key1 = minScrollPosition, key2 = density) {
        density.run { minScrollPosition.roundToPx() }
    }
    val maxPx = remember(key1 = maxScrollPosition, key2 = density) {
        density.run { maxScrollPosition.roundToPx() }
    }
    val coroutineScope = rememberCoroutineScope()
    val state = remember(minPx, maxPx, composePosition, coroutineScope, onScrollStop) {
        when (composePosition) {
            ComposePosition.Start, ComposePosition.Top -> ChainScrollableComponentState(
                (minPx - maxPx).toFloat(),
                0f,
                composePosition,
                coroutineScope,
                onScrollStop,
            )
            ComposePosition.End, ComposePosition.Bottom -> ChainScrollableComponentState(
                0f,
                (maxPx - minPx).toFloat(),
                composePosition,
                coroutineScope,
                onScrollStop,
            )
        }
    }
    //滚动监听,如果当前方向可以被优先使用,则不传手势给content
    val nestedScrollState = remember(
        composePosition,
        minScrollPosition,
        maxScrollPosition,
        chainMode,
    ) {
        when (chainMode) {
            ChainMode.ContentFirst -> ContentFirstNestedScrollConnection(state)
            ChainMode.ChainContentFirst -> ChainContentFirstNestedScrollConnection(state)
        }
    }
    Box(
        modifier
            .nestedScroll(nestedScrollState)
            .clipScrollableContainer(composePosition.orientation),
        contentAlignment = when (composePosition) {
            ComposePosition.Start -> Alignment.CenterStart
            ComposePosition.End -> Alignment.CenterEnd
            ComposePosition.Top -> Alignment.TopCenter
            ComposePosition.Bottom -> Alignment.BottomCenter
        }
    ) {
        content(state)
        chainContent(state)
    }
}