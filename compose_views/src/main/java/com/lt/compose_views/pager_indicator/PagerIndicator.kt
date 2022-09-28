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

package com.lt.compose_views.pager_indicator

import androidx.annotation.IntRange
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * creator: lt  2022/6/27  lt.dygzs@qq.com
 * effect : 适用于Pager的指示器
 * warning:
 * @param size 指示器数量
 * @param offsetPercentWithSelect 选中的指示器的偏移百分比
 * @param selectIndex 选中的索引
 * @param indicatorItem 未被选中的指示器
 * @param selectIndicatorItem 被选中的指示器
 * @param modifier 修饰
 * @param margin 指示器之间的间距(两边也有,保证即使选中的指示器较大,也不容易超出控件区域)
 * @param orientation 指示器排列方向
 */
@Composable
fun PagerIndicator(
    @IntRange(from = 1) size: Int,
    offsetPercentWithSelect: Float,
    selectIndex: Int,
    indicatorItem: @Composable (index: Int) -> Unit,
    selectIndicatorItem: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    margin: Dp = 8.dp,
    orientation: Orientation = Orientation.Horizontal,
) {
    if (size < 1) return
    val density = LocalDensity.current
    //indicatorItem的中间位置坐标
    val indicatorItemCenters = remember(size) {
        IntArray(size)
    }
    Layout(modifier = modifier, content = {
        selectIndicatorItem()
        repeat(size) {
            indicatorItem(it)
        }
    }, measurePolicy = { measurableList, constraints ->
        val marginPx = density.run { margin.roundToPx() }
        val mConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val selectPlaceable = measurableList.first().measure(mConstraints)
        var width = 0
        var height = 0
        val isHorizontal = orientation == Orientation.Horizontal
        //测量indicatorItem,并获取其占用的宽高
        val placeableList = (1 until measurableList.size).mapIndexed { index, i ->
            val placeable = measurableList[i].measure(mConstraints)
            if (isHorizontal) {
                if (index == 0) {
                    width = marginPx
                }
                indicatorItemCenters[i - 1] = width + placeable.width / 2
                width += placeable.width + marginPx
                height = maxOf(height, placeable.height)
            } else {
                if (index == 0) {
                    height = marginPx
                }
                indicatorItemCenters[i - 1] = height + placeable.height / 2
                width = maxOf(width, placeable.width)
                height += placeable.height + marginPx
            }
            placeable
        }
        width = maxOf(width, selectPlaceable.width)
        height = maxOf(height, selectPlaceable.height)
        layout(width, height) {
            //放置indicatorItem
            var coordinate = 0
            placeableList.forEachIndexed { index, placeable ->
                if (index == 0)
                    coordinate += marginPx
                coordinate += if (isHorizontal) {
                    placeable.placeRelative(coordinate, (height - placeable.height) / 2)
                    placeable.width + marginPx
                } else {
                    placeable.placeRelative((width - placeable.width) / 2, coordinate)
                    placeable.height + marginPx
                }
            }
            //放置selectIndicatorItem
            selectPlaceable.placeRelative(
                x = if (isHorizontal) {
                    //当前索引的中间坐标
                    val currCenter = indicatorItemCenters[selectIndex]
                    //是否是往下一页翻
                    val isNext = offsetPercentWithSelect >= 0
                    //起始的x轴
                    val startX = currCenter - selectPlaceable.width / 2
                    //当前索引到下一个索引的差值(偏移量)
                    var difference =
                        indicatorItemCenters.getOrElse(selectIndex + if (isNext) 1 else -1) { currCenter } - currCenter
                    if (!isNext)
                        difference = 0 - difference
                    //计算最终的x轴(起始x轴+偏移的x轴)
                    (startX + difference * offsetPercentWithSelect).roundToInt()
                } else
                    (width - selectPlaceable.width) / 2,
                y = if (isHorizontal)
                    (height - selectPlaceable.height) / 2
                else {
                    //当前索引的中间坐标
                    val currCenter = indicatorItemCenters[selectIndex]
                    //是否是往下一页翻
                    val isNext = offsetPercentWithSelect >= 0
                    //起始的y轴
                    val startY = currCenter - selectPlaceable.height / 2
                    //当前索引到下一个索引的差值(偏移量)
                    var difference =
                        indicatorItemCenters.getOrElse(selectIndex + if (isNext) 1 else -1) { currCenter } - currCenter
                    if (!isNext)
                        difference = 0 - difference
                    //计算最终的x轴(起始x轴+偏移的x轴)
                    (startY + difference * offsetPercentWithSelect).roundToInt()
                },
            )
        }
    })
}