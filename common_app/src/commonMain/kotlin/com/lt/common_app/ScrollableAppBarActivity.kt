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

package com.lt.common_app

import M
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.lt.common_app.base.BaseComposeActivity
import com.lt.compose_views.chain_scrollable_component.ChainScrollableComponent
import com.lt.compose_views.chain_scrollable_component.ChainScrollableComponentState
import com.lt.compose_views.chain_scrollable_component.mode.ChainMode
import com.lt.compose_views.chain_scrollable_component.scrollable_appbar.ScrollableAppBar
import com.lt.compose_views.other.FpsText
import com.lt.compose_views.util.ComposePosition
import com.lt.ltttttttttttt.common_app.generated.resources.Res
import com.lt.ltttttttttttt.common_app.generated.resources.back
import com.lt.ltttttttttttt.common_app.generated.resources.top_bar_bk
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import kotlin.math.abs
import kotlin.math.roundToInt

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/5 22:24
// Description: 
// Documentation:

class ScrollableAppBarActivity : BaseComposeActivity() {
    private var composePosition by mutableStateOf(ComposePosition.Top)
    private var chainMode by mutableStateOf(ChainMode.ChainContentFirst)
    private val maxDp = 200.dp
    private val minDp = 56.dp

    @Composable
    override fun ComposeContent() {
        Column(modifier = Modifier.fillMaxSize()) {
            AppBar()
            Menu()
            ChainScrollable()
        }
    }

    @Composable
    private fun Menu() {
        Row {
            Text(text = "方向:$composePosition")
            Button(onClick = {
                composePosition = when (chainMode) {
                    ChainMode.ChainContentFirst, ChainMode.ChainAfterContent -> when (composePosition) {
                        ComposePosition.Top -> ComposePosition.Bottom
                        ComposePosition.Bottom -> ComposePosition.Start
                        ComposePosition.Start -> ComposePosition.End
                        ComposePosition.End -> ComposePosition.Top
                    }

                    ChainMode.ContentFirst -> when (composePosition) {
                        ComposePosition.Top, ComposePosition.Bottom -> ComposePosition.Start
                        ComposePosition.Start, ComposePosition.End -> ComposePosition.Top
                    }
                }
            }) {
                Text(text = "切方向")
            }
            FpsText(modifier = Modifier)
            Text(text = "模式:$chainMode", M.width(80.dp))
            Button(onClick = {
                chainMode = when (chainMode) {
                    ChainMode.ChainContentFirst -> {
                        composePosition = ComposePosition.Top
                        ChainMode.ContentFirst
                    }

                    ChainMode.ContentFirst -> ChainMode.ChainAfterContent
                    ChainMode.ChainAfterContent -> ChainMode.ChainContentFirst
                }
            }) {
                Text(text = "切模式")
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ColumnScope.ChainScrollable() {
        ChainScrollableComponent(
            minScrollPosition = minDp,
            maxScrollPosition = maxDp,
            chainContent = { state ->
                Box(
                    modifier = Modifier
                        .let {
                            if (composePosition.isHorizontal())
                                it
                                    .fillMaxHeight()
                                    .width(maxDp)
                            else
                                it
                                    .fillMaxWidth()
                                    .height(maxDp)
                        }
                        .offset {
                            when (composePosition) {
                                ComposePosition.Start -> IntOffset(
                                    state
                                        .getScrollPositionValue()
                                        .roundToInt(),
                                    0
                                )

                                ComposePosition.End -> IntOffset(
                                    state
                                        .getScrollPositionValue()
                                        .roundToInt(),
                                    0
                                )

                                ComposePosition.Top -> IntOffset(
                                    0,
                                    state
                                        .getScrollPositionValue()
                                        .roundToInt()
                                )

                                ComposePosition.Bottom -> IntOffset(
                                    0,
                                    state
                                        .getScrollPositionValue()
                                        .roundToInt()
                                )
                            }
                        }
                        .background(Color.LightGray)
                ) {
                    Text(
                        text = "${state.getScrollPositionValue()}  ${state.getScrollPositionPercentage()}",
                        modifier = Modifier.align(
                            when (composePosition) {
                                ComposePosition.Start -> Alignment.CenterEnd
                                ComposePosition.End -> Alignment.CenterStart
                                ComposePosition.Top -> Alignment.BottomCenter
                                ComposePosition.Bottom -> Alignment.TopCenter
                            }
                        )
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            chainMode = chainMode,
            composePosition = composePosition,
        ) {
            if (false) {
                LazyColumn(
                    contentPadding = PaddingValues(top = maxDp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(100) {
                        Text("item $it")
                    }
                }
            } else {
                if (composePosition.isHorizontal())
                    Row(
                        Modifier
                            .fillMaxSize()
                            .horizontalScroll(rememberScrollState())
                            .padding(
                                start = if (composePosition == ComposePosition.Start) maxDp else 0.dp,
                                top = if (composePosition == ComposePosition.Top) maxDp else 0.dp,
                                end = if (composePosition == ComposePosition.End) maxDp else 0.dp,
                                bottom = if (composePosition == ComposePosition.Bottom) maxDp else 0.dp,
                            )
                    ) {
                        repeat(30) {
                            Text("item $it")
                        }
                    }
                else
                    Column(
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(
                                start = if (composePosition == ComposePosition.Start) maxDp else 0.dp,
                                top = if (composePosition == ComposePosition.Top) maxDp else 0.dp,
                                end = if (composePosition == ComposePosition.End) maxDp else 0.dp,
                                bottom = if (composePosition == ComposePosition.Bottom) maxDp else 0.dp,
                            )
                    ) {
                        repeat(100) {
                            Text("item $it")
                        }
                    }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ColumnScope.AppBar() {
        val lazyListState = rememberLazyListState()
        ScrollableAppBar(
            title = "toolbar",
            background = painterResource(Res.drawable.top_bar_bk),
            navigationIcon = {
                Image(painterResource(Res.drawable.back), "back", colorFilter = ColorFilter.tint(Color.White))
            },
            maxScrollPosition = maxDp,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            //onScrollStop = scrollStop(lazyListState)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(top = maxDp),
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
            ) {
                items(30) { index ->
                    Text(
                        "I'm item $index", modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }

    //停止拖动时,使appbar归位
    private fun scrollStop(lazyListState: LazyListState): (ChainScrollableComponentState, Float) -> Boolean =
        function@{ state, delta ->
            val percentage = state.getScrollPositionPercentage()
            if (percentage == 1f || percentage == 0f)
                return@function true
            state.coroutineScope.launch {
                val startPositionValue =
                    abs((state.getScrollPositionValue() + delta) / (state.maxPx - state.minPx))
                if (percentage > 0.5f) {
                    state.setScrollPositionWithAnimate(state.minPx - state.maxPx)
                    lazyListState.animateScrollBy(startPositionValue - state.minPx)
                } else {
                    state.setScrollPositionWithAnimate(0f)
                    lazyListState.animateScrollBy(startPositionValue)
                }
            }
            true
        }
}