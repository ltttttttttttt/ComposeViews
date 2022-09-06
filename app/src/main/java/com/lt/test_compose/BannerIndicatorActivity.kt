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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.compose_views.banner.Banner
import com.lt.compose_views.banner.rememberBannerState
import com.lt.compose_views.banner_indicator.PagerIndicator
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.rememberComposePagerState
import com.lt.compose_views.flow_layout.FlowLayout
import com.lt.compose_views.other.VerticalSpace
import com.lt.test_compose.base.BaseComposeActivity
import com.lt.test_compose.base.M

/**
 * creator: lt  2022/9/5  lt.dygzs@qq.com
 * effect :
 * warning:
 */
class BannerIndicatorActivity : BaseComposeActivity() {
    private val colors = mutableStateListOf(
        Color(150, 105, 61, 255),
        Color(122, 138, 55, 255),
        Color(50, 134, 74, 255),
        Color(112, 62, 11, 255),
        Color(114, 61, 101, 255),
    )
    private val orientation = mutableStateOf(Orientation.Horizontal)

    override fun getTitleText(): String = "BannerIndicator"

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
            }

            BannerView()
            VerticalSpace(dp = 10)
            PagerView()
        }
    }

    @Composable
    private fun BannerView() {
        val bannerState = rememberBannerState()
        val offsetPercent by remember { bannerState.createChildOffsetPercentFlow() }
            .collectAsState(initial = 0f)
        Box(
            modifier = M
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Banner(
                colors.size,
                M.fillMaxSize(),
                bannerState = bannerState,
                autoScrollTime = 1000,
                orientation = orientation.value,
            ) {
                Box(
                    modifier = M
                        .fillMaxSize()
                        .background(colors.getOrNull(index) ?: Color.Black)
                ) {
                    Button(onClick = {
                        Toast.makeText(
                            this@BannerIndicatorActivity,
                            "index=$index",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }, modifier = M.align(Alignment.Center)) {
                        Text(text = this@Banner.index.toString(), fontSize = 30.sp)
                    }
                }
            }
            PagerIndicator(
                size = colors.size,
                offsetPercentWithSelect = offsetPercent,
                selectIndex = bannerState.getCurrSelectIndex(),
                indicatorItem = {
                    Spacer(
                        modifier = M
                            .size(10.dp)
                            .background(
                                Color.Gray,
                                CircleShape
                            )
                    )
                },
                selectIndicatorItem = {
                    Spacer(
                        modifier = M
                            .size(10.dp)
                            .background(Color.Black, CircleShape)
                    )
                },
                modifier = M
                    .align(Alignment.BottomCenter)
                    .padding(10.dp),
                orientation = orientation.value,
            )
        }
    }

    @Composable
    private fun PagerView() {
        val pagerState = rememberComposePagerState()
        val offsetPercent by remember { pagerState.createChildOffsetPercentFlow() }
            .collectAsState(initial = 0f)
        Box(
            modifier = M
                .fillMaxWidth()
                .height(200.dp)
        ) {
            ComposePager(
                colors.size,
                M.fillMaxSize(),
                composePagerState = pagerState,
                orientation = orientation.value,
            ) {
                Box(
                    modifier = M
                        .fillMaxSize()
                        .background(colors.getOrNull(index) ?: Color.Black)
                ) {
                    Button(onClick = {
                        pagerState.setPageIndexWithAnim(
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
            PagerIndicator(
                size = colors.size,
                offsetPercentWithSelect = offsetPercent,
                selectIndex = pagerState.getCurrSelectIndex(),
                indicatorItem = {
                    Spacer(
                        modifier = M
                            .size(10.dp)
                            .background(
                                Color.Gray,
                                CircleShape
                            )
                    )
                },
                selectIndicatorItem = {
                    Spacer(
                        modifier = M
                            .size(10.dp)
                            .background(Color.Black, CircleShape)
                    )
                },
                modifier = M
                    .align(Alignment.BottomCenter)
                    .padding(10.dp),
                orientation = orientation.value,
            )
        }
    }
}