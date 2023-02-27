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

package com.lt.compose_views.chain_scrollable_component.scrollable_appbar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.compose_views.chain_scrollable_component.ChainMode
import com.lt.compose_views.chain_scrollable_component.ChainScrollableComponent
import com.lt.compose_views.chain_scrollable_component.ChainScrollableComponentState
import com.lt.compose_views.util.ComposePosition
import kotlin.math.roundToInt


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/5 22:15
// Description: 
// Documentation:

/**
 * 可伸缩顶部导航栏
 * Scalable top navigation bar
 *
 * @param title 顶部导航栏标题
 *              Title of top bar
 * @param background 背景图片
 *                   Background of top bar
 * @param modifier 修饰
 * @param onScrollStop 停止滚动时回调
 *                     Callback of scroll stop event
 * @param minScrollPosition 最小滚动位置(距离指定方向的顶点)
 *                          Minimum scroll position
 * @param maxScrollPosition 最大滚动位置(距离指定方向的顶点)
 *                          Maximum scroll position
 * @param navigationIcon 顶部导航栏图标，默认为返回键
 *                       Icon of top bar
 * @param composePosition 设置bar布局所在的位置,并且间接指定了滑动方向
 *                        Set the position of the top bar layout
 * @param chainMode 联动方式
 *                  Chain mode
 * @param content compose内容区域,需要内容是在相应方向可滚动的,并且需要自行给内容设置相应方向的PaddingValues或padding
 *                Content of compose
 */
@ExperimentalFoundationApi
@Composable
fun ScrollableAppBar(
    title: String,
    background: Painter,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (state: ChainScrollableComponentState) -> Unit =
        remember {
            {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "ArrowBack",
                    tint = Color.White
                )
            }
        },
    minScrollPosition: Dp = 56.dp,
    maxScrollPosition: Dp = 200.dp,
    onScrollStop: ((state: ChainScrollableComponentState) -> Unit)? = null,
    composePosition: ComposePosition = ComposePosition.Top,
    chainMode: ChainMode = ChainMode.ChainContentFirst,
    content: @Composable BoxScope.(state: ChainScrollableComponentState) -> Unit,
) {
    val density = LocalDensity.current
    // Title 偏移量参考值
    val titleOffsetWidthReferenceValue =
        remember { density.run { navigationIconSize.roundToPx().toFloat() } }
    ChainScrollableComponent(
        modifier = modifier,
        minScrollPosition = minScrollPosition,
        maxScrollPosition = maxScrollPosition,
        onScrollStop = onScrollStop,
        composePosition = composePosition,
        chainMode = chainMode,
        chainContent = { state ->
            Box(
                modifier = Modifier
                    .height(maxScrollPosition)
                    .fillMaxWidth()
                    .offset {
                        IntOffset(
                            0,
                            state
                                .getScrollPositionValue()
                                .roundToInt()
                        )
                    }
            ) {
                Image(
                    painter = background,
                    contentDescription = "background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // 导航图标
                Box(
                    modifier = Modifier
                        .width(navigationIconSize)
                        .height(minScrollPosition)
                        .offset {
                            IntOffset(
                                x = 0,
                                y = -state
                                    .getScrollPositionValue()
                                    .roundToInt() //保证应用栏是始终不动的
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    navigationIcon(state)
                }
                Box(
                    modifier = Modifier
                        .height(minScrollPosition) //和ToolBar同高
                        .align(Alignment.BottomStart)
                        .offset {
                            IntOffset(
                                x = (state.getScrollPositionPercentage() * titleOffsetWidthReferenceValue).roundToInt(),
                                y = 0
                            )
                        },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        modifier = Modifier.padding(start = 20.dp),
                        fontSize = 20.sp
                    )
                }
            }
        }, content = content
    )
}

// 导航图标大小
private val navigationIconSize = 50.dp