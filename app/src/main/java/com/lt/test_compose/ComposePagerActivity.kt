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

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.rememberComposePagerState
import com.lt.compose_views.flow_layout.FlowLayout
import com.lt.test_compose.base.BaseComposeActivity
import com.lt.test_compose.base.M
import com.lt.test_compose.base.composeClick
import kotlin.random.Random

class ComposePagerActivity : BaseComposeActivity() {
    private val colors = mutableStateListOf(
        Color(150, 105, 61, 255),
        Color(122, 138, 55, 255),
        Color(50, 134, 74, 255),
        Color(112, 62, 11, 255),
        Color(114, 61, 101, 255),
    )
    private val orientation = mutableStateOf(Orientation.Vertical)

    override fun getTitleText(): String = "ComposePager"

    @Composable
    override fun ComposeContent() {
        val composePagerState = rememberComposePagerState()
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

            ComposePager(
                colors.size,
                M.fillMaxSize(),
                composePagerState = composePagerState,
                orientation = orientation.value,
            ) {
                Box(
                    modifier = M
                        .fillMaxSize()
                        .background(colors[index])
                ) {
                    Button(composeClick {
                        composePagerState.setPageIndexWithAnim(
                            if (index + 1 >= colors.size)
                                0
                            else
                                index + 1
                        )
                    }, modifier = M.align(Alignment.Center)) {
                        Text(text = this@ComposePager.index.toString(), fontSize = 30.sp)
                    }
                }
            }
        }
    }

}