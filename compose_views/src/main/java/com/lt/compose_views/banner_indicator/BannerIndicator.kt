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

package com.lt.compose_views.banner_indicator

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.lt.compose_views.modifier.offsetPercent
import kotlin.math.roundToInt

/**
 * creator: lt  2022/6/27  lt.dygzs@qq.com
 * effect : 适用于Pager的指示器
 * warning:
 * [size]指示器数量
 * [offsetPercentWithSelect]选中的指示器的偏移百分比
 * [selectIndex]选中的索引
 * [indicatorItem]未被选中的指示器
 * [selectIndicatorItem]被选中的指示器
 * [modifier]修饰
 * [margin]指示器之间的间距
 * [orientation]指示器排列方向
 */
@Composable
fun PagerIndicator(
    size: Int,
    offsetPercentWithSelect: Float,
    selectIndex: Int,
    indicatorItem: @Composable (index: Int) -> Unit,
    selectIndicatorItem: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    margin: Dp = 8.dp,
    orientation: Orientation = Orientation.Horizontal,
) {
    val density = LocalDensity.current
    val marginPx by remember(margin) {
        mutableStateOf(density.run { margin.roundToPx() })
    }
    val maxOffset = remember(marginPx) {
        (size - 1).toFloat() to ((size - 1) * marginPx)
    }
    val minOffset = remember {
        0f to 0
    }
    Box(
        modifier,
        contentAlignment = if (orientation == Orientation.Horizontal) Alignment.CenterStart else Alignment.TopCenter
    ) {
        //未选中的指示器
        if (orientation == Orientation.Horizontal) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IndicatorItems(size, indicatorItem, margin)
            }
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IndicatorItems(size, indicatorItem, margin)
            }
        }
        //选中的指示器
        Box(
            Modifier
                .offsetPercent(
                    xOffsetPercent = if (orientation == Orientation.Horizontal) offsetPercentWithSelect + selectIndex else 0f,
                    yOffsetPercent = if (orientation == Orientation.Vertical) offsetPercentWithSelect + selectIndex else 0f,
                    pxOffset = IntOffset(
                        if (orientation == Orientation.Horizontal) (marginPx * (selectIndex + offsetPercentWithSelect)).roundToInt() else 0,
                        if (orientation == Orientation.Vertical) (marginPx * (selectIndex + offsetPercentWithSelect)).roundToInt() else 0,
                    ),
                    maxOffset = maxOffset,
                    minOffset = minOffset,
                )
        ) {
            selectIndicatorItem()
        }
    }
}

//未选中的指示器条目
@Composable
private fun IndicatorItems(
    size: Int,
    indicatorItem: @Composable (index: Int) -> Unit,
    margin: Dp
) {
    repeat(size) {
        indicatorItem(it)
        if (it < size - 1)
            Spacer(modifier = Modifier.size(margin))
    }
}