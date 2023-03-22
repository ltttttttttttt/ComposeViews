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
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.common_app.base.BaseComposeActivity
import com.lt.common_app.base.composeClick
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.ComposePagerState
import com.lt.compose_views.compose_pager.NestedComposePager
import com.lt.compose_views.compose_pager.rememberComposePagerState
import com.lt.compose_views.flow_layout.FlowLayout
import com.lt.compose_views.other.FpsText

class NestedComposePagerActivity : BaseComposeActivity() {
    private val colors = mutableStateListOf(
        Color(150, 105, 61, 255),
        Color(122, 138, 55, 255),
        Color(50, 134, 74, 255),
        Color(112, 62, 11, 255),
        Color(114, 61, 101, 255),
    )

    @Composable
    override fun ComposeContent() {
        val composePagerState = rememberComposePagerState()
        Column(M.fillMaxSize()) {
            Menu()
            ComposePagerSample(composePagerState)
        }
    }

    @Composable
    private fun ComposePagerSample(composePagerState: ComposePagerState) {
        NestedComposePager(
            colors.size,
            M.fillMaxSize(),
            composePagerState = composePagerState,
            orientation = Orientation.Horizontal,
            pageCache = 2,
        ) {
            LazyRow(
                modifier = M
                    .fillMaxSize()
                    .background(colors[index])
            ) {
                items(20) {
                    Button(composeClick {
                        composePagerState.setPageIndexWithAnimate(
                            if (index + 1 >= colors.size)
                                0
                            else
                                index + 1
                        )
                    }) {
                        Text(text = it.toString(), fontSize = 30.sp)
                    }
                }
            }
        }
    }

    @Composable
    private fun Menu() {
        FlowLayout(horizontalMargin = 10.dp) {
            FpsText()
        }
    }
}