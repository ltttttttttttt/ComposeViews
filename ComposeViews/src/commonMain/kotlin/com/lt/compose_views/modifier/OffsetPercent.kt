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

package com.lt.compose_views.modifier

import androidx.compose.foundation.layout.offset
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.lt.compose_views.util.midOf
import kotlin.math.roundToInt

/**
 * creator: lt  2022/9/6  lt.dygzs@qq.com
 * effect : 偏移自身宽度或高度的百分比
 *          Offset by a percentage of its own width or height
 * warning:
 * @param xOffsetPercent x轴偏移量百分比
 *                       Offset percent of x
 * @param yOffsetPercent y轴偏移量百分比
 *                       Offset percent of y
 * @param pxOffset 正常基于px的偏移量
 *                 Offset px
 * @param maxOffset 最大偏移量: 百分比 + px
 *                  max offset
 * @param minOffset 最小偏移量: 百分比 + px
 *                  min offset
 */
fun Modifier.offsetPercent(
    xOffsetPercent: Float,
    yOffsetPercent: Float,
    pxOffset: IntOffset = IntOffset.Zero,
    maxOffset: Pair<Float, Int>? = null,
    minOffset: Pair<Float, Int>? = null,
): Modifier {
    var size = IntSize.Zero
    return layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        size = IntSize(placeable.width, placeable.height)
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }.offset {
        IntOffset(
            midOf(
                getMinOffset(minOffset, size, true),
                (xOffsetPercent * size.width).toInt() + pxOffset.x,
                getMaxOffset(maxOffset, size, true)
            ),
            midOf(
                getMinOffset(minOffset, size, false),
                (yOffsetPercent * size.height).toInt() + pxOffset.y,
                getMaxOffset(maxOffset, size, false)
            ),
        )
    }
}

//获取最大的偏移量
private fun getMaxOffset(maxOffset: Pair<Float, Int>?, size: IntSize, isWidth: Boolean): Int {
    maxOffset ?: return Int.MAX_VALUE
    val value = if (isWidth) size.width else size.height
    return (maxOffset.first * value).roundToInt() + maxOffset.second
}

//获取最小的偏移量
private fun getMinOffset(minOffset: Pair<Float, Int>?, size: IntSize, isWidth: Boolean): Int {
    minOffset ?: return Int.MIN_VALUE
    val value = if (isWidth) size.width else size.height
    return (minOffset.first * value).roundToInt() + minOffset.second
}