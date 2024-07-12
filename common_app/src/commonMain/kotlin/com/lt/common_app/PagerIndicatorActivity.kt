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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.common_app.base.BaseComposeActivity
import com.lt.compose_views.banner.Banner
import com.lt.compose_views.banner.rememberBannerState
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.rememberComposePagerState
import com.lt.compose_views.compose_pager.rememberScalePagerContentTransformation
import com.lt.compose_views.flow_layout.FlowLayout
import com.lt.compose_views.image_banner.ImageBanner
import com.lt.compose_views.other.FpsText
import com.lt.compose_views.other.VerticalSpace
import com.lt.compose_views.pager_indicator.PagerIndicator
import rememberPainter

/**
 * creator: lt  2022/9/5  lt.dygzs@qq.com
 * effect :
 * warning:
 */
class PagerIndicatorActivity : BaseComposeActivity() {
    private val colors = mutableStateListOf(
        Color(150, 105, 61, 255),
        Color(122, 138, 55, 255),
        Color(50, 134, 74, 255),
        Color(112, 62, 11, 255),
        Color(114, 61, 101, 255),
    )
    private val orientation = mutableStateOf(Orientation.Horizontal)

    @Composable
    override fun ComposeContent() {
        Column(M.fillMaxSize()) {
            Menu()
            BannerView()
            VerticalSpace(dp = 10)
            BannerViewTransformation()
            VerticalSpace(dp = 10)
            PagerView()
            VerticalSpace(dp = 10)
            Images()
        }
    }

    @Composable
    private fun Menu() {
        FlowLayout(horizontalMargin = 10.dp) {
            FpsText(modifier = Modifier)
            Button(onClick = {
                orientation.value = if (orientation.value == Orientation.Horizontal)
                    Orientation.Vertical
                else
                    Orientation.Horizontal
            }) {
                Text(text = "改变滑动方向")
            }
        }
    }

    @Composable
    private fun BannerView() {
        val bannerState = rememberBannerState()
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
                bannerKey = { colors[it].toString() }
            ) {
                Box(
                    modifier = M
                        .fillMaxSize()
                        .background(colors.getOrNull(index) ?: Color.Black)
                ) {
                    Button(onClick = {
                        "index=$index".showToast()
                    }, modifier = M.align(Alignment.Center)) {
                        Text(text = this@Banner.index.toString(), fontSize = 30.sp)
                    }
                }
            }
            PagerIndicator(
                size = colors.size,
                offsetPercentWithSelectFlow = remember { bannerState.createChildOffsetPercentFlow() },
                selectIndexFlow = remember { bannerState.createCurrSelectIndexFlow() },
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
    private fun BannerViewTransformation() {
        Banner(
            colors.size,
            M.fillMaxWidth()
                .height(100.dp)
                .padding(horizontal = 60.dp),
            autoScrollTime = 1000,
            orientation = orientation.value,
            bannerKey = { colors[it].toString() },
            clip = false,
            contentTransformation = rememberScalePagerContentTransformation(1f, 0.8f)
        ) {
            Box(
                modifier = M
                    .fillMaxSize()
                    .background(colors.getOrNull(index) ?: Color.Black, RoundedCornerShape(10.dp))
            )
        }
    }

    @Composable
    private fun PagerView() {
        val pagerState = rememberComposePagerState()
        Box(
            modifier = M
                .fillMaxWidth()
                .height(100.dp)
        ) {
            ComposePager(
                colors.size,
                M.fillMaxSize(),
                composePagerState = pagerState,
                orientation = orientation.value,
                pagerKey = { colors[it].toString() }
            ) {
                Box(
                    modifier = M
                        .fillMaxSize()
                        .background(colors.getOrNull(index) ?: Color.Black)
                ) {
                    Button(onClick = {
                        pagerState.setPageIndexWithAnimate(
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
                offsetPercentWithSelectFlow = remember { pagerState.createChildOffsetPercentFlow() },
                selectIndexFlow = remember { pagerState.createCurrSelectIndexFlow() },
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

    val images = listOf(
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F66b7ce397068c1f4710cafe4e1827ab5f7565180.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665065304&t=c45a94bbac62a3bd502dc53e40afc583",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2Fc0793b2877f09ded49e96e3b3e05781d4f1e2e9e.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665065303&t=86940ecb5bf20d1c90b5fb1c1f0afb06",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F79c593c97cb1aef62160a7c6165ea3ecdc60f064.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665065301&t=0d840bcfe779bcac4ea65db6d257c417",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F2726b76584c11dc75449024ad6105893be1edd0f.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665065300&t=7afae3a8eccb498fb37d54ff54d2016b",
    )

    @Composable
    private fun Images() {
        ImageBanner(
            imageSize = images.size,
            imageContent = {
                Image(
                    painter = rememberPainter(data = images[index]),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onItemClick(index) },
                    contentScale = ContentScale.Crop
                )
            },
            indicatorItem = {
                Spacer(
                    modifier = M
                        .size(6.dp, 6.dp)
                        .background(Color.Gray, CircleShape)
                )
            },
            selectIndicatorItem = {
                Spacer(
                    modifier = M
                        .size(12.dp, 6.dp)
                        .background(Color(72, 199, 108), CircleShape)
                )
            },
            modifier = M
                .fillMaxWidth()
                .height(200.dp),
            orientation = orientation.value,
            autoScrollTime = 1500,
        )
    }

    private fun onItemClick(index: Int) {
        "index=$index".showToast()
    }
}