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

package com.lt.compose_views.flow_layout

import android.util.Log
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lt.compose_views.util.IntArrayList
import com.lt.compose_views.util.NotPlace
import com.lt.compose_views.util.midOf

/**
 * creator: lt  2022/7/4  lt.dygzs@qq.com
 * effect : 可以自动换行的线性布局
 * warning:
 * [modifier]修饰
 * [orientation]排列的方向,[Orientation.Horizontal]时会先横向排列,一排放不下会换到下一行继续横向排列
 * [horizontalAlignment]子级在横向上的位置
 * [verticalArrangement]子级在竖向上的位置
 * [horizontalMargin]子级与子级在横向上的间距
 * [verticalMargin]子级与子级在竖向上的间距
 * [maxLines]最多能放多少行(或列)
 * [content]compose内容区域
 */
@Composable
fun FlowLayout(
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Horizontal,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalMargin: Dp = 0.dp,
    verticalMargin: Dp = 0.dp,
    maxLines: Int = Int.MAX_VALUE,
    content: @Composable () -> Unit
) {
    val horizontalMarginPx = LocalDensity.current.run { horizontalMargin.roundToPx() }
    val verticalMarginPx = LocalDensity.current.run { verticalMargin.roundToPx() }
    Layout(content, modifier) { measurables, constraints ->
        val maxWidth = constraints.maxWidth
        val maxHeight = constraints.maxHeight
        val isHorizontal = orientation == Orientation.Horizontal
        val mConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        //保存每一行(列)的宽度或最大宽度
        val linesWidth = IntArrayList()
        val linesHeight = IntArrayList()
        //布局每一行(列)的子项的数量(超过maxLines后的就不布局了)
        val linesSize = IntArrayList()
        var lineSize = 0
        var lineWidth = 0
        var lineHeight = 0

        val placeables = measurables.map {
            if (linesSize.size >= maxLines) {
                return@map NotPlace
            }
            val placeable = it.measure(mConstraints)
            if (isHorizontal) {
                if (lineWidth + placeable.width + horizontalMarginPx > maxWidth) {
                    linesWidth.add(lineWidth)
                    linesHeight.add(lineHeight)
                    linesSize.add(lineSize)
                    lineWidth = 0
                    lineHeight = 0
                    lineSize = 0
                }
                lineSize++
                lineWidth += placeable.width + horizontalMarginPx
                lineHeight = maxOf(lineHeight, placeable.height)
            } else {
                if (lineHeight + placeable.height + verticalMarginPx > maxHeight) {
                    linesWidth.add(lineWidth)
                    linesHeight.add(lineHeight)
                    linesSize.add(lineSize)
                    lineWidth = 0
                    lineHeight = 0
                    lineSize = 0
                }
                lineSize++
                lineWidth = maxOf(lineWidth, placeable.width)
                lineHeight += placeable.height + verticalMarginPx
            }
            placeable
        }
        if (linesSize.size < maxLines) {
            linesWidth.add(lineWidth)
            linesHeight.add(lineHeight)
            linesSize.add(lineSize)
        }

        //计算宽度:
        //宽度=midOf(最小宽度限制,自适应宽度,最大宽度限制)
        //自适应宽度=if(横向排列){所有列中最宽的一个}else{所有宽度的总和+每列的间距}
        val width = midOf(
            constraints.minWidth,
            if (isHorizontal) {
                linesWidth.toIntArray().maxOrNull() ?: 0
            } else {
                linesWidth.toIntArray().sum() + maxOf(
                    0,
                    (linesWidth.size - 1) * horizontalMarginPx
                )
            },
            constraints.maxWidth
        )
        val height = midOf(
            constraints.minHeight,
            if (isHorizontal) {
                linesHeight.toIntArray().sum() + maxOf(
                    0,
                    (linesHeight.size - 1) * verticalMarginPx
                )
            } else {
                linesHeight.toIntArray().maxOrNull() ?: 0
            },
            constraints.maxHeight
        )
        layout(width, height) {
            // TODO by lt 2022/7/4 18:13 使用对齐
            Log.w("lllttt", ".FlowLayout 104 : $linesSize   $linesWidth    $linesHeight")
            var index = 0
            var lineStartWidth = 0
            var lineStartHeight = 0
            linesSize.forEachIndexed { line, lineSize ->
                repeat(lineSize) { lineIndex ->
                    val placeable = placeables[index]
                    Log.w(
                        "lllttt",
                        ".FlowLayout 106 : $index   $lineStartWidth    $lineStartHeight"
                    )
                    placeable.placeRelative(
                        x = lineStartWidth,
                        y = lineStartHeight
                    )
                    if (isHorizontal) {
                        lineStartWidth += placeable.width + horizontalMarginPx
                    } else {
                        lineStartHeight += placeable.height + verticalMarginPx
                    }
                    index++
                }
                if (isHorizontal) {
                    lineStartWidth = 0
                    lineStartHeight += linesHeight[line] + verticalMarginPx
                } else {
                    lineStartWidth += linesWidth[line] + horizontalMarginPx
                    lineStartHeight = 0
                }
            }
        }
    }
}