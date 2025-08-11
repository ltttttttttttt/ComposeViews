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
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.common_app.base.BaseComposeActivity
import com.lt.common_app.base.click
import com.lt.common_app.base.composeClick
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.ComposePagerState
import com.lt.compose_views.flow_layout.FlowLayout
import com.lt.compose_views.other.FpsText
import com.lt.compose_views.refresh_layout.RefreshContentStateEnum
import com.lt.compose_views.refresh_layout.RefreshLayoutState
import com.lt.compose_views.refresh_layout.VerticalRefreshableLayout
import com.lt.compose_views.refresh_layout.rememberRefreshLayoutState
import com.lt.ltttttttttttt.common_app.generated.resources.Res
import com.lt.ltttttttttttt.common_app.generated.resources.back
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import kotlin.random.Random

class ComposePagerActivity : BaseComposeActivity() {
    private val colors = mutableStateListOf(
        Color(150, 105, 61, 255),
        Color(122, 138, 55, 255),
        Color(50, 134, 74, 255),
        Color(112, 62, 11, 255),
        Color(114, 61, 101, 255),
    )
    private val images = mutableStateListOf(
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F66b7ce397068c1f4710cafe4e1827ab5f7565180.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665065304&t=c45a94bbac62a3bd502dc53e40afc583",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2Fc0793b2877f09ded49e96e3b3e05781d4f1e2e9e.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665065303&t=86940ecb5bf20d1c90b5fb1c1f0afb06",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F79c593c97cb1aef62160a7c6165ea3ecdc60f064.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665065301&t=0d840bcfe779bcac4ea65db6d257c417",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F2726b76584c11dc75449024ad6105893be1edd0f.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665065300&t=7afae3a8eccb498fb37d54ff54d2016b",
    )
    private val orientation = mutableStateOf(Orientation.Vertical)
    private var isImage by mutableStateOf(false)

    @Composable
    override fun ComposeContent() {
        val composePagerState = remember(orientation.value) { ComposePagerState() }
        Column(M.fillMaxSize()) {
            Menu()
            VerticalRefreshableLayout(
                topRefreshLayoutState = rememberRefreshLayoutState(
                    onRefreshListener = onRefresh()
                ),
                bottomRefreshLayoutState = rememberRefreshLayoutState(
                    onRefreshListener = onRefresh()
                ),
                topUserEnable = composePagerState.getCurrSelectIndex() == 0,
                bottomUserEnable = composePagerState.getCurrSelectIndex() == (if (isImage) images.size else colors.size) - 1,
            ) {
                ComposePagerSample(composePagerState)
            }
        }
    }

    @Composable
    private fun ComposePagerSample(composePagerState: ComposePagerState) {
        ComposePager(
            if (isImage) images.size else colors.size,
            M.fillMaxSize(),
            composePagerState = composePagerState,
            orientation = orientation.value,
            pageCache = 2,
            pagerKey = { index ->
                if (isImage) images[index] else colors[index].toString()
            }
        ) {
            if (isImage) {
                //看日志测试pageCache
                DisposableEffect(key1 = index, effect = {
                    println("ComposePagerActivity.ComposeContent=: $index")
                    onDispose {
                        println("ComposePagerActivity.onDispose=: $index")
                    }
                })
                Image(
                    painter = painterResource(Res.drawable.back),
                    contentDescription = "",
                    modifier = M
                        .fillMaxSize()
                        .click {
                            composePagerState.setPageIndexWithAnimate(
                                if (index + 1 >= images.size)
                                    0
                                else
                                    index + 1
                            )
                        },
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = M
                        .fillMaxSize()
                        .background(colors[index])
                ) {
                    Button(composeClick {
                        composePagerState.setPageIndexWithAnimate(
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

    @Composable
    private fun Menu() {
        FlowLayout(horizontalMargin = 10.dp) {
            FpsText()
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
                if (isImage) {
                    images.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F66b7ce397068c1f4710cafe4e1827ab5f7565180.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665065304&t=c45a94bbac62a3bd502dc53e40afc583")
                    images.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2Fc0793b2877f09ded49e96e3b3e05781d4f1e2e9e.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665065303&t=86940ecb5bf20d1c90b5fb1c1f0afb06")
                    images.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F79c593c97cb1aef62160a7c6165ea3ecdc60f064.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665065301&t=0d840bcfe779bcac4ea65db6d257c417")
                    images.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F2726b76584c11dc75449024ad6105893be1edd0f.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665065300&t=7afae3a8eccb498fb37d54ff54d2016b")
                } else
                    colors.add(Color(Random.nextLong()))
            }) {
                Text(text = "增加条目")
            }
            Button(onClick = {
                if (isImage)
                    images.removeLastOrNull()
                else
                    colors.removeLastOrNull()
            }) {
                Text(text = "减少条目")
            }
            Text("条目数量:${if (isImage) images.size else colors.size}")
            Button(onClick = {
                isImage = !isImage
            }) {
                Text(text = "切换")
            }
            Button({
                jump(ContentPaddingA::class)
            }) {
                Text("contentPadding")
            }
        }
    }

    @Composable
    private fun onRefresh(): RefreshLayoutState.() -> Unit =
        {
            mainScope.launch {
                delay(2000)
                setRefreshState(RefreshContentStateEnum.Stop)
            }
        }

}