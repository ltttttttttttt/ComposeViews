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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.lt.compose_views.scrollable_appbar.ChainScrollableComponent
import com.lt.compose_views.scrollable_appbar.ScrollableAppBar
import com.lt.compose_views.util.ComposePosition
import com.lt.compose_views.util.rememberMutableStateOf
import com.lt.test_compose.base.BaseComposeActivity

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/5 22:24
// Description: 
// Documentation:

class ScrollableAppBarActivity : BaseComposeActivity() {
    private var composePosition by mutableStateOf(ComposePosition.Top)

    override fun getTitleText(): String {
        return "ScrollableAppBar"
    }

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
                    Text(text = "切换方向")
                }
                FpsMonitor(modifier = Modifier)
            }
            ChainScrollable()
        }
    }

    @Composable
    fun ColumnScope.ChainScrollable() {
        ChainScrollableComponent(
            minScrollPosition = 56.dp,
            maxScrollPosition = 200.dp,
            chainContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.LightGray)
                ) {
                    Text(
                        text = it.getScrollPositionValue().toString(),
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            composePosition = composePosition,
            isSupportCanNotScrollCompose = true,
        ) {
            Column {
                for (i in 0..20) {
                    Row {
                        for (j in 0..20) {
                            Spacer(
                                modifier = Modifier
                                    .size(25.dp)
                                    .background(
                                        Color(
                                            red = i * 10,
                                            green = j * 10,
                                            blue = minOf(i * j, 255)
                                        )
                                    )
                            )
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ColumnScope.AppBar() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), color = MaterialTheme.colors.background
        ) {
            // ToolBar 最大向上位移量
            // 56.dp 参考自 androidx.compose.material AppBar.kt 里面定义的 private val AppBarHeight = 56.dp
            val maxUpPx = with(LocalDensity.current) {
                200.dp.roundToPx().toFloat() - 56.dp.roundToPx().toFloat()
            }
            // ToolBar 最小向上位移量
            val minUpPx = 0f
            // 偏移折叠工具栏上移高度
            val toolbarOffsetHeightPx = rememberMutableStateOf(0f)
            // 现在，让我们创建与嵌套滚动系统的连接并聆听子 LazyColumn 中发生的滚动
            val nestedScrollConnection = remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(
                        available: Offset, source: NestedScrollSource
                    ): Offset {
                        // try to consume before LazyColumn to collapse toolbar if needed, hence pre-scroll
                        val delta = available.y
                        val newOffset = toolbarOffsetHeightPx.value + delta
                        toolbarOffsetHeightPx.value = newOffset.coerceIn(-maxUpPx, minUpPx)
                        // here's the catch: let's pretend we consumed 0 in any case, since we want
                        // LazyColumn to scroll anyway for good UX
                        // We're basically watching scroll without taking it
                        return Offset.Zero
                    }
                }
            }
            Box(
                Modifier
                    .fillMaxSize()
                    // attach as a parent to the nested scroll system
                    .nestedScroll(nestedScrollConnection)
            ) {
                // our list with build in nested scroll support that will notify us about its scroll
                LazyColumn(contentPadding = PaddingValues(top = 200.dp)) {
                    items(100) { index ->
                        Text(
                            "I'm item $index", modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
                ScrollableAppBar(
                    title = "toolbar offset is ${toolbarOffsetHeightPx.value}",
                    scrollableAppBarHeight = 200.dp,
                    toolbarOffsetHeightPx = toolbarOffsetHeightPx,
                    backgroundImageId = R.drawable.top_bar_bk
                )
            }
        }
    }
}