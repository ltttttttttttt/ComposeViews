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

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/9/5 22:15
// Description: 
// Documentation:

/**
 * 可伸缩顶部导航栏
 *
 * @param modifier
 * @param title 顶部导航栏标题
 * @param navigationIcon 顶部导航栏图标，默认为返回键
 * @param backgroundImageId 顶部导航栏背景图片
 * @param background
 * @param scrollableAppBarHeight 顶部导航栏高度
 * @param toolbarOffsetHeightPx 顶部导航栏向上偏移量
 */
@Composable
fun ScrollableAppBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationIcon: @Composable (() -> Unit) =
        { Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "ArrowBack",tint = Color.White) },
    @DrawableRes backgroundImageId:Int,
    background: Color = MaterialTheme.colors.primary,
    scrollableAppBarHeight: Dp,
    toolbarOffsetHeightPx: MutableState<Float>
){

    // 应用栏最大向上偏移量
    val maxOffsetHeightPx = with(LocalDensity.current) { scrollableAppBarHeight.roundToPx().toFloat() - toolBarHeight.roundToPx().toFloat() }
    // Title 偏移量参考值
    val titleOffsetWidthReferenceValue = with(LocalDensity.current) { navigationIconSize.roundToPx().toFloat() }

    Box(modifier = Modifier
        .height(scrollableAppBarHeight)
        .offset {
            IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) //设置偏移量
        }
        .fillMaxWidth()
    ) {
        Image(painter = painterResource(id = backgroundImageId), contentDescription = "background", contentScale = ContentScale.FillBounds)

        // 自定义应用栏
        Row(
            modifier = modifier
                .offset {
                    IntOffset(
                        x = 0,
                        y = -toolbarOffsetHeightPx.value.roundToInt() //保证应用栏是始终不动的
                    )
                }
                .height(toolBarHeight)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 导航图标
            Box(modifier = Modifier.size(navigationIconSize),contentAlignment = Alignment.Center) {
                navigationIcon()
            }
        }

        Box(
            modifier = Modifier
                .height(toolBarHeight) //和ToolBar同高
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .offset {
                    IntOffset(
                        x = -((toolbarOffsetHeightPx.value / maxOffsetHeightPx) * titleOffsetWidthReferenceValue).roundToInt(),
                        y = 0
                    )
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = title,color = Color.White,modifier = Modifier.padding(start = 20.dp),fontSize = 20.sp)
        }
    }
}

// 应用栏高度
private val toolBarHeight = 56.dp
// 导航图标大小
private val navigationIconSize = 50.dp