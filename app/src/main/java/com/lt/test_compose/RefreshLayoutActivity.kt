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

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lt.compose_views.flow_layout.FlowLayout
import com.lt.compose_views.other.VerticalSpace
import com.lt.compose_views.refresh_layout.RefreshContentStateEnum
import com.lt.compose_views.refresh_layout.RefreshLayout
import com.lt.compose_views.refresh_layout.RefreshLayoutState
import com.lt.compose_views.refresh_layout.rememberRefreshLayoutState
import com.lt.compose_views.util.ComposePosition
import com.lt.test_compose.base.BaseComposeActivity
import com.lt.test_compose.base.M

class RefreshLayoutActivity : BaseComposeActivity() {
    private val handler = Handler(Looper.getMainLooper())

    override fun getTitleText(): String = "RefreshLayout"

    @Composable
    override fun ComposeContent() {
        val topRefreshState = createState()
        val bottomRefreshState = createState()
        val startRefreshState = createState()
        val endRefreshState = createState()
        LaunchedEffect(key1 = Unit) {
            topRefreshState.setRefreshState(RefreshContentStateEnum.Refreshing)
            bottomRefreshState.setRefreshState(RefreshContentStateEnum.Refreshing)
            startRefreshState.setRefreshState(RefreshContentStateEnum.Refreshing)
            endRefreshState.setRefreshState(RefreshContentStateEnum.Refreshing)
        }

        Column(
            M
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            FlowLayout(horizontalMargin = 10.dp) {
                FpsMonitor(modifier = Modifier)
                Text(text = "Top状态:${topRefreshState.getRefreshContentState().value}")
                Text(text = "Bottom状态:${bottomRefreshState.getRefreshContentState().value}")
                Text(text = "Start状态:${startRefreshState.getRefreshContentState().value}")
                Text(text = "End状态:${endRefreshState.getRefreshContentState().value}")
            }

            TopRefreshLayout(topRefreshState)
            VerticalSpace(dp = 20)
            BottomRefreshLayout(bottomRefreshState)
            VerticalSpace(dp = 20)
            StartRefreshLayout(startRefreshState)
            VerticalSpace(dp = 20)
            EndRefreshLayout(endRefreshState)
        }
    }

    @Composable
    private fun createState() = rememberRefreshLayoutState {
        runOnUiThread {
            Toast.makeText(this@RefreshLayoutActivity, "刷新了", Toast.LENGTH_SHORT).show()
        }
        handler.postDelayed({
            setRefreshState(RefreshContentStateEnum.Stop)
        }, 2000)
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun TopRefreshLayout(refreshState: RefreshLayoutState) {
        RefreshLayout(
            {
                Box(M.fillMaxWidth()) {
                    Text(
                        text = "下拉刷新",
                        modifier = M
                            .background(Color.Red)
                            .align(Alignment.Center)
                    )
                }
            },
            refreshLayoutState = refreshState,
            composePosition = ComposePosition.Top,
        ) {
            Column(
                modifier = M
                    .width(200.dp)
                    .height(100.dp)
                    .background(Color.Gray)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(text = "内容区域1")
                Text(text = "内容区域2")
                Text(text = "内容区域3")
                Text(text = "内容区域4")
                Text(text = "内容区域5")
                Text(text = "内容区域6")
                Text(text = "内容区域7")
                Text(text = "内容区域8")
                Text(text = "内容区域9")
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun BottomRefreshLayout(refreshState: RefreshLayoutState) {
        RefreshLayout(
            {
                Box(M.fillMaxWidth()) {
                    Text(
                        text = "下拉刷新",
                        modifier = M
                            .background(Color.Red)
                            .align(Alignment.Center)
                    )
                }
            },
            refreshLayoutState = refreshState,
            composePosition = ComposePosition.Bottom,
        ) {
            Column(
                modifier = M
                    .width(200.dp)
                    .height(100.dp)
                    .background(Color.Gray)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(text = "内容区域1")
                Text(text = "内容区域2")
                Text(text = "内容区域3")
                Text(text = "内容区域4")
                Text(text = "内容区域5")
                Text(text = "内容区域6")
                Text(text = "内容区域7")
                Text(text = "内容区域8")
                Text(text = "内容区域9")
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun StartRefreshLayout(refreshState: RefreshLayoutState) {
        RefreshLayout(
            {
                Box(M.fillMaxHeight()) {
                    Text(
                        text = "下拉刷新",
                        modifier = M
                            .background(Color.Red)
                            .align(Alignment.Center)
                    )
                }
            },
            refreshLayoutState = refreshState,
            composePosition = ComposePosition.Start,
        ) {
            Row(
                modifier = M
                    .width(200.dp)
                    .height(100.dp)
                    .background(Color.Gray)
                    .horizontalScroll(rememberScrollState())
            ) {
                Text(text = "内容区域1")
                Text(text = "内容区域2")
                Text(text = "内容区域3")
                Text(text = "内容区域4")
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun EndRefreshLayout(refreshState: RefreshLayoutState) {
        RefreshLayout(
            {
                Box(M.fillMaxHeight()) {
                    Text(
                        text = "下拉刷新",
                        modifier = M
                            .background(Color.Red)
                            .align(Alignment.Center)
                    )
                }
            },
            refreshLayoutState = refreshState,
            composePosition = ComposePosition.End,
            dragEfficiency = 2f,
            refreshContentThreshold = 100.dp
        ) {
            Row(
                modifier = M
                    .width(200.dp)
                    .height(100.dp)
                    .background(Color.Gray)
                    .horizontalScroll(rememberScrollState())
            ) {
                Text(text = "内容区域1")
                Text(text = "内容区域2")
                Text(text = "内容区域3")
                Text(text = "内容区域4")
            }
        }
    }

}