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

package com.lt.compose_views.refresh_layout.refresh_content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lt.compose_views.refresh_layout.RefreshLayoutState
import kotlin.math.abs

/**
 * creator: lt  2022/10/8  lt.dygzs@qq.com
 * effect : 刷新组件,一个椭圆
 *          An ellipse refresh component
 * warning:
 * @param min 最小宽度或高度
 *            Minimum width or height
 * @param color 椭圆的颜色
 *              Color of ellipse
 * @param content 自行放置额外的内容
 *                Put extra content
 * @param innerContent 椭圆中可放置的内容
 *                     Content in ellipse
 */
@Composable
fun RefreshLayoutState.EllipseRefreshContent(
    min: Dp = minDp,
    color: Color = Color.Black,
    content: @Composable (BoxScope.(RefreshLayoutState) -> Unit)? = null,
    innerContent: @Composable (BoxScope.(RefreshLayoutState) -> Unit)? = null,
) {
    val isHorizontal =
        remember(getComposePositionState()) { getComposePositionState().value.isHorizontal() }
    val density = LocalDensity.current
    val min_2 = remember(min) { min / 2 }
    val min_4 = remember(min) { min / 4 }
    Box(
        modifier = if (isHorizontal)
            Modifier
                .fillMaxHeight()
                .padding(horizontal = min_4)
        else
            Modifier
                .fillMaxWidth()
                .padding(vertical = min_4)
    ) {
        Box(
            modifier = Modifier
                .let {
                    if (isHorizontal)
                        it
                            .height(min)
                            .width(
                                maxOf(
                                    density.run { abs(getRefreshContentOffset()).toDp() } - min_2,
                                    min
                                )
                            )
                    else
                        it
                            .width(min)
                            .height(
                                maxOf(
                                    density.run { abs(getRefreshContentOffset()).toDp() } - min_2,
                                    min
                                )
                            )
                }
                .border(
                    border = BorderStroke(
                        width = 2.dp,
                        color = color,
                    ), shape = CircleShape
                )
                .align(Alignment.Center)
        ) {
            innerContent?.invoke(this, this@EllipseRefreshContent)
        }
        content?.invoke(this, this@EllipseRefreshContent)
    }
}

private val minDp = 20.dp