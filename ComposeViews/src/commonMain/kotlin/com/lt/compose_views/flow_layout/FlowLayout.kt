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

package com.lt.compose_views.flow_layout

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lt.compose_views.util.NotPlace
import com.lt.compose_views.util.midOf
import com.lt.data_structure.basic_value.IntArrayList

/**
 * creator: lt  2022/7/4  lt.dygzs@qq.com
 * effect : 可以自动换行的线性布局
 *          Linear layout with word wrapping
 * warning:
 * @param modifier 修饰
 * @param orientation 排列的方向,[Orientation.Horizontal]时会先横向排列,一排放不下会换到下一行继续横向排列
 *                    Direction of arrangement
 * @param horizontalAlignment 子级在横向上的位置
 *                            Alignment of horizontal
 * @param verticalAlignment 子级在竖向上的位置
 *                          Alignment of vertical
 * @param horizontalMargin 子级与子级在横向上的间距
 *                          Margin of horizontal
 * @param verticalMargin 子级与子级在竖向上的间距
 *                       Margin of vertical
 * @param maxLines 最多能放多少行(或列)
 *                 How many lines can be placed
 * @param content compose内容区域
 *                Content of compose
 */
@Composable
fun FlowLayout(
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Horizontal,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    horizontalMargin: Dp = 0.dp,
    verticalMargin: Dp = 0.dp,
    maxLines: Int = Int.MAX_VALUE,
    content: @Composable () -> Unit
) {
    val horizontalMarginPx = LocalDensity.current.run { horizontalMargin.roundToPx() }
    val verticalMarginPx = LocalDensity.current.run { verticalMargin.roundToPx() }
    Layout(content, modifier) { measurableList, constraints ->
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

        val placeableList = measurableList.map {
            //如果超过了最大行数限制,后续元素不测量且不放置
            if (linesSize.size >= maxLines) {
                return@map NotPlace
            }
            //测量并计算每一行(列)能放多少元素,并记录下来元素放置的信息
            val placeable = it.measure(mConstraints)
            if (isHorizontal) {
                //如果当前行放不下,就换行
                if (lineWidth + placeable.width + horizontalMarginPx > maxWidth) {
                    linesWidth.add(lineWidth)
                    linesHeight.add(lineHeight)
                    linesSize.add(lineSize)
                    lineWidth = 0
                    lineHeight = 0
                    lineSize = 0
                }
                //记录当前行数据
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
        //如果没有超过最大行数限制,就记录最后一行的数据
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
            var index = 0
            var lineStartWidth = 0
            var lineStartHeight = 0
            //遍历每一行有多少元素,并双重遍历每一行内元素数量进行放置
            linesSize.forEachIndexed { line, lineSize ->
                //这一行(列)的子元素相对于父元素的对齐方式
                val lineWidth = linesWidth[line]
                val lineHeight = linesHeight[line]
                if (isHorizontal) {
                    when (horizontalAlignment) {
                        Alignment.Start -> lineStartWidth = 0
                        Alignment.CenterHorizontally -> lineStartWidth =
                            (width - lineWidth) / 2

                        Alignment.End -> lineStartWidth = width - lineWidth
                    }
                } else {
                    when (verticalAlignment) {
                        Alignment.Top -> lineStartHeight = 0
                        Alignment.CenterVertically -> lineStartHeight =
                            (height - lineHeight) / 2

                        Alignment.Bottom -> lineStartHeight = height - lineHeight
                    }
                }
                repeat(lineSize) { lineIndex ->
                    val placeable = placeableList[index]
                    //这个子元素相对于这一行(列)的对齐方式
                    val xOffset = when {
                        isHorizontal -> 0
                        verticalAlignment == Alignment.Top -> 0
                        verticalAlignment == Alignment.CenterVertically -> (lineWidth - placeable.width) / 2
                        verticalAlignment == Alignment.Bottom -> lineWidth - placeable.width
                        else -> 0
                    }
                    val yOffset = when {
                        !isHorizontal -> 0
                        horizontalAlignment == Alignment.Start -> 0
                        horizontalAlignment == Alignment.CenterHorizontally -> (lineHeight - placeable.height) / 2
                        horizontalAlignment == Alignment.End -> lineHeight - placeable.height
                        else -> 0
                    }
                    //按照行或列放置元素
                    placeable.placeRelative(
                        x = lineStartWidth + xOffset,
                        y = lineStartHeight + yOffset
                    )
                    if (isHorizontal) {
                        lineStartWidth += placeable.width + horizontalMarginPx
                    } else {
                        lineStartHeight += placeable.height + verticalMarginPx
                    }
                    index++
                }
                //一行(列)放置完毕,换行
                if (isHorizontal) {
                    lineStartHeight += linesHeight[line] + verticalMarginPx
                } else {
                    lineStartWidth += linesWidth[line] + horizontalMarginPx
                }
            }
        }
    }
}