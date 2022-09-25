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

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.other.VerticalSpace
import com.lt.compose_views.refresh_layout.*
import com.lt.compose_views.util.ComposePosition
import com.lt.compose_views.util.rememberMutableStateOf
import com.lt.test_compose.base.BaseComposeActivity
import com.lt.test_compose.base.M
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RefreshLayoutActivity : BaseComposeActivity() {

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

        Row {
            Column(
                M
                    .fillMaxHeight()
                    .width(260.dp)
                    .background(Color.LightGray)
            ) {
                Column() {
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
            Column(M.fillMaxSize()) {
                MyPullToRefresh()
                VerticalSpace(dp = 20)
                MyRefreshableLazyColumn()
                VerticalSpace(dp = 20)
                MyRefreshablePager()
            }
        }
    }

    private val colors = mutableStateListOf(
        Color(150, 105, 61, 255),
        Color(122, 138, 55, 255),
        Color(50, 134, 74, 255),
        Color(112, 62, 11, 255),
        Color(114, 61, 101, 255),
    )

    @Composable
    private fun MyRefreshablePager() {
        VerticalRefreshableLayout(
            topRefreshLayoutState = createState(),
            bottomRefreshLayoutState = createState()
        ) {
            ComposePager(pageCount = colors.size, orientation = Orientation.Vertical) {
                Box(
                    modifier = M
                        .fillMaxSize()
                        .background(colors[index])
                ) {
                    Text(text = index.toString(), modifier = M.align(Alignment.Center))
                }
            }
        }
    }

    @Composable
    private fun MyRefreshableLazyColumn() {
        var isLoadFinish by rememberMutableStateOf(value = false)
        VerticalRefreshableLayout(
            //顶部刷新的状态
            topRefreshLayoutState = createState(),
            //底部刷新的状态
            bottomRefreshLayoutState = rememberRefreshLayoutState(onRefreshListener = {
                if (isLoadFinish) {
                    setRefreshState(RefreshContentStateEnum.Stop)
                    return@rememberRefreshLayoutState
                }
                mainScope.launch {
                    Toast.makeText(this@RefreshLayoutActivity, "加载数据了", Toast.LENGTH_SHORT).show()
                    delay(2000)
                    setRefreshState(RefreshContentStateEnum.Stop)
                    isLoadFinish = true
                }
            }), modifier = M
                .fillMaxWidth()
                .height(300.dp),
            bottomIsLoadFinish = isLoadFinish
        ) {
            LazyColumn(modifier = M.fillMaxSize(), content = {
                repeat(20) {
                    item(key = it) {
                        Text(text = "内容区域${it + 1}")
                    }
                }
            })
        }
    }

    @Composable
    private fun MyPullToRefresh() {
        PullToRefresh(refreshLayoutState = createState()) {
            Column(
                M
                    .fillMaxWidth()
                    .height(100.dp)
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

    @Composable
    private fun createState() = rememberRefreshLayoutState {
        mainScope.launch {
            Toast.makeText(this@RefreshLayoutActivity, "刷新了", Toast.LENGTH_SHORT).show()
            delay(2000)
            setRefreshState(RefreshContentStateEnum.Stop)
        }
    }

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