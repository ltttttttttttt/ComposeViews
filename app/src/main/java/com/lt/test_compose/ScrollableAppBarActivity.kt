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

package com.lt.test_compose

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.lt.compose_views.chain_scrollable_component.ChainMode
import com.lt.compose_views.chain_scrollable_component.ChainScrollableComponent
import com.lt.compose_views.chain_scrollable_component.ChainScrollableComponentState
import com.lt.compose_views.util.ComposePosition
import com.lt.test_compose.base.BaseComposeActivity
import kotlinx.coroutines.launch
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

    override fun getTitleText(): String {
        return "ScrollableAppBar"
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun ComposeContent() {
        Column(modifier = Modifier.fillMaxSize()) {
            AppBar()
            Row {
                Text(text = "方向:$composePosition")
                Button(onClick = {
                    composePosition = when (composePosition) {
                        ComposePosition.Top -> ComposePosition.Bottom
                        ComposePosition.Bottom -> ComposePosition.Start
                        ComposePosition.Start -> ComposePosition.End
                        ComposePosition.End -> ComposePosition.Top
                    }
                }) {
                    Text(text = "切方向")
                }
                FpsMonitor(modifier = Modifier)
                Text(text = "模式:${chainMode.toString().substring(0, 5)}")
                Button(onClick = {
                    chainMode =
                        if (chainMode == ChainMode.ChainContentFirst) ChainMode.ContentFirst else ChainMode.ChainContentFirst
                }) {
                    Text(text = "切模式")
                }
            }
            ChainScrollable()
        }
    }

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
                        repeat(100) {
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

    @Composable
    fun ColumnScope.AppBar() {
        val lazyListState = rememberLazyListState()
        com.lt.compose_views.chain_scrollable_component.scrollable_appbar.ScrollableAppBar(
            title = "toolbar",
            background = painterResource(id = R.drawable.top_bar_bk),
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
                items(100) { index ->
                    Text(
                        "I'm item $index", modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }

    //停止拖动时,使appbar归位
    private fun scrollStop(lazyListState: LazyListState): (ChainScrollableComponentState) -> Unit =
        function@{ state ->
            val percentage = state.getScrollPositionPercentage()
            if (percentage == 1f || percentage == 0f)
                return@function
            state.coroutineScope.launch {
                val startPositionValue = state.getScrollPositionValue()
                if (percentage > 0.5f) {
                    state.setScrollPositionWithAnimate(state.minPx - state.maxPx)
                    lazyListState.animateScrollBy(startPositionValue - state.minPx)
                } else {
                    state.setScrollPositionWithAnimate(0f)
                    lazyListState.animateScrollBy(startPositionValue)
                }
            }
        }
}