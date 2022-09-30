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

package com.lt.compose_views.scrollable_appbar

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
import com.lt.compose_views.util.ComposePosition
import kotlin.math.roundToInt


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/5 22:15
// Description: 
// Documentation:

/**
 * 可伸缩顶部导航栏
 *
 * @param title 顶部导航栏标题
 * @param background 背景图片
 * @param modifier 修饰
 * @param navigationIcon 顶部导航栏图标，默认为返回键
 * @param minScrollPosition 最小滚动位置(距离指定方向的顶点)
 * @param maxScrollPosition 最大滚动位置(距离指定方向的顶点)
 * @param composePosition 设置bar布局所在的位置,并且间接指定了滑动方向
 * @param chainMode 联动方式
 * @param content compose内容区域
 */
@OptIn(ExperimentalFoundationApi::class)
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
    composePosition: ComposePosition = ComposePosition.Top,
    chainMode: ChainMode = ChainMode.ContentFirst,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    // Title 偏移量参考值
    val titleOffsetWidthReferenceValue =
        remember { density.run { navigationIconSize.roundToPx().toFloat() } }
    ChainScrollableComponent(
        modifier = modifier,
        minScrollPosition = minScrollPosition,
        maxScrollPosition = maxScrollPosition,
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
                // 自定义应用栏
                Row(
                    modifier = modifier
                        .offset {
                            IntOffset(
                                x = 0,
                                y = -state
                                    .getScrollPositionValue()
                                    .roundToInt() //保证应用栏是始终不动的
                            )
                        }
                        .height(minScrollPosition)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 导航图标
                    Box(
                        modifier = Modifier.size(navigationIconSize),
                        contentAlignment = Alignment.Center
                    ) {
                        navigationIcon(state)
                    }
                }
                Box(
                    modifier = Modifier
                        .height(minScrollPosition) //和ToolBar同高
                        .fillMaxWidth()
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