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
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lt.compose_views.flow_layout.FlowLayout
import com.lt.compose_views.refresh_layout.RefreshLayout
import com.lt.compose_views.refresh_layout.rememberRefreshLayoutState
import com.lt.test_compose.base.BaseComposeActivity
import com.lt.test_compose.base.M
import kotlin.random.Random

class RefreshLayoutActivity : BaseComposeActivity() {
    private val colors = mutableStateListOf(
        Color(150, 105, 61, 255),
        Color(122, 138, 55, 255),
        Color(50, 134, 74, 255),
        Color(112, 62, 11, 255),
        Color(114, 61, 101, 255),
    )
    private val orientation = mutableStateOf(Orientation.Vertical)

    override fun getTitleText(): String = "RefreshLayout"

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun ComposeContent() {
        Column(M.fillMaxSize()) {
            FlowLayout(horizontalMargin = 10.dp) {
                FpsMonitor(modifier = Modifier)
                Button(onClick = {
                    orientation.value = if (orientation.value == Orientation.Horizontal)
                        Orientation.Vertical
                    else
                        Orientation.Horizontal
                }) {
                    Text(text = "改变滑动方向")
                }
                Text(text = "当前滑动方向:${orientation.value}")
                Button(onClick = {
                    colors.add(Color(Random.nextLong()))
                }) {
                    Text(text = "增加条目")
                }
                Button(onClick = {
                    colors.removeLastOrNull()
                }) {
                    Text(text = "减少条目")
                }
                Text("当前条目数量:${colors.size}")
            }

            RefreshLayout({
                Text(text = "下拉刷新", modifier = M.background(Color.Red))
            }, rememberRefreshLayoutState {

            }) {
                Column(
                    modifier = M
                        .fillMaxWidth()
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
    }

}