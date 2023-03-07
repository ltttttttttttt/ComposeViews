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

package com.lt.compose_views.pager_indicator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.lt.compose_views.util.getPercentageValue
import com.lt.compose_views.util.rememberMutableStateOf
import kotlinx.coroutines.flow.Flow
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * creator: lt  2022/10/23  lt.dygzs@qq.com
 * effect : 适用于Pager的文本指示器
 *          Text indicator for pager
 * warning:
 * @param texts 文本列表
 *              The text list
 * @param offsetPercentWithSelectFlow 选中的指示器的偏移百分比
 *                                     The offset percentage of the selected indicator
 * @param selectIndexFlow 选中的索引
 *                        The index of selected indicator
 * @param fontSize 未被选中的文字大小
 *                  Font size of the text indicator
 * @param selectFontSize 被选中的文字大小
 *                       Font size of the selected text indicator
 * @param textColor 未被选中的文字颜色
 *                  Font color of the text indicator
 * @param selectTextColor 被选中的文字颜色
 *                        Font color of the selected text indicator
 * @param selectIndicatorColor 指示器的颜色
 *                             Color of the indicator
 * @param onIndicatorClick 指示器的点击事件
 *                         Click event of the text indicator
 * @param modifier 修饰
 * @param margin 指示器之间的间距(两边也有,保证即使选中的指示器较大,也不容易超出控件区域)
 *               Spacing between the text indicators
 * @param userCanScroll 用户是否可以滚动
 *                      Whether the user can scroll
 */
@Composable
fun TextPagerIndicator(
    texts: List<String>,
    offsetPercentWithSelectFlow: Flow<Float>,
    selectIndexFlow: Flow<Int>,
    fontSize: TextUnit,
    selectFontSize: TextUnit,
    textColor: Color,
    selectTextColor: Color,
    selectIndicatorColor: Color,
    onIndicatorClick: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    margin: Dp = 8.dp,
    userCanScroll: Boolean = true,
) {
    val density = LocalDensity.current
    val dp20 = remember {
        density.run { 20.dp.toPx() }
    }
    TextPagerIndicator(
        texts = texts,
        offsetPercentWithSelectFlow = offsetPercentWithSelectFlow,
        selectIndexFlow = selectIndexFlow,
        fontSize = fontSize,
        selectFontSize = selectFontSize,
        textColor = textColor,
        selectTextColor = selectTextColor,
        onIndicatorClick = onIndicatorClick,
        selectIndicatorItem = {
            var width by rememberMutableStateOf(0.dp)
            val offsetPercentWithSelect by offsetPercentWithSelectFlow.collectAsState(0f)
            val selectIndex by selectIndexFlow.collectAsState(0)
            LaunchedEffect(texts, offsetPercentWithSelect, selectIndex) {
                width = density.run {
                    //当前选中的指示器宽度
                    val width =
                        maxOf(dp20, indicatorsInfo.getIndicatorSize(selectIndex) - dp20)
                    if (offsetPercentWithSelect == 0f)
                        return@run width.toDp()
                    val index = selectIndex + if (offsetPercentWithSelect > 0) 1 else -1
                    //将要选中的指示器宽度
                    val toWidth =
                        maxOf(dp20, indicatorsInfo.getIndicatorSize(index) - dp20)
                    //通过百分比计算出实际宽度
                    abs(offsetPercentWithSelect).getPercentageValue(width, toWidth).toDp()
                }
            }
            Box(modifier = Modifier.fillMaxHeight()) {
                Spacer(
                    modifier = Modifier
                        .size(width, 3.dp)
                        .align(Alignment.BottomCenter)
                        .background(selectIndicatorColor, CircleShape)
                )
            }
        },
        modifier = modifier,
        margin = margin,
        userCanScroll = userCanScroll,
    )
}

//[selectIndicatorItem] 被选中的指示器
@Composable
fun TextPagerIndicator(
    texts: List<String>,
    offsetPercentWithSelectFlow: Flow<Float>,
    selectIndexFlow: Flow<Int>,
    fontSize: TextUnit,
    selectFontSize: TextUnit,
    textColor: Color,
    selectTextColor: Color,
    onIndicatorClick: (index: Int) -> Unit,
    selectIndicatorItem: @Composable PagerIndicatorScope.() -> Unit,
    modifier: Modifier = Modifier,
    margin: Dp = 8.dp,
    userCanScroll: Boolean = true,
) {
    val density = LocalDensity.current
    val fontPx by remember(fontSize) {
        mutableStateOf(density.run { fontSize.toPx() })
    }
    val selectFontPx by remember(selectFontSize) {
        mutableStateOf(density.run { selectFontSize.toPx() })
    }
    PagerIndicator(
        size = texts.size,
        offsetPercentWithSelectFlow = offsetPercentWithSelectFlow,
        selectIndexFlow = selectIndexFlow,
        indicatorItem = { index ->
            val selectIndex by selectIndexFlow.collectAsState(0)
            Box(modifier = Modifier
                .fillMaxHeight()
                .clickable {
                    if (index != selectIndex)
                        onIndicatorClick(index)
                }) {
                val offsetPercentWithSelect by offsetPercentWithSelectFlow.collectAsState(0f)
                val (size, color) = remember(
                    index,
                    selectIndex,
                    offsetPercentWithSelect,
                    selectFontSize,
                    fontSize,
                    textColor,
                    selectTextColor,
                ) {
                    val percent = abs(selectIndex + offsetPercentWithSelect - index)
                    if (percent > 1f)
                        return@remember fontSize to textColor
                    density.run {
                        percent.getPercentageValue(selectFontPx, fontPx).roundToInt().toSp()
                    } to percent.getPercentageValue(selectTextColor, textColor)
                }
                Text(
                    text = texts[index],
                    fontSize = size,
                    color = color,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        },
        selectIndicatorItem = selectIndicatorItem,
        modifier = modifier,
        margin = margin,
        orientation = Orientation.Horizontal,
        userCanScroll = userCanScroll,
    )
}

@Deprecated("因为重组的性能原因,请使用其他同名(重载)函数  For performance reasons of reorganization, use other functions with the same name (overloaded)")
@Composable
fun TextPagerIndicator(
    texts: List<String>,
    offsetPercentWithSelect: Float,
    selectIndex: Int,
    fontSize: TextUnit,
    selectFontSize: TextUnit,
    textColor: Color,
    selectTextColor: Color,
    selectIndicatorColor: Color,
    onIndicatorClick: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    margin: Dp = 8.dp,
    userCanScroll: Boolean = true,
) {
    val density = LocalDensity.current
    val dp20 = remember {
        density.run { 20.dp.toPx() }
    }
    TextPagerIndicator(
        texts = texts,
        offsetPercentWithSelect = offsetPercentWithSelect,
        selectIndex = selectIndex,
        fontSize = fontSize,
        selectFontSize = selectFontSize,
        textColor = textColor,
        selectTextColor = selectTextColor,
        onIndicatorClick = onIndicatorClick,
        selectIndicatorItem = {
            var width by rememberMutableStateOf(0.dp)
            LaunchedEffect(texts, offsetPercentWithSelect, selectIndex) {
                width = density.run {
                    //当前选中的指示器宽度
                    val width =
                        maxOf(dp20, indicatorsInfo.getIndicatorSize(selectIndex) - dp20)
                    if (offsetPercentWithSelect == 0f)
                        return@run width.toDp()
                    val index = selectIndex + if (offsetPercentWithSelect > 0) 1 else -1
                    //将要选中的指示器宽度
                    val toWidth =
                        maxOf(dp20, indicatorsInfo.getIndicatorSize(index) - dp20)
                    //通过百分比计算出实际宽度
                    abs(offsetPercentWithSelect).getPercentageValue(width, toWidth).toDp()
                }
            }
            Box(modifier = Modifier.fillMaxHeight()) {
                Spacer(
                    modifier = Modifier
                        .size(width, 3.dp)
                        .align(Alignment.BottomCenter)
                        .background(selectIndicatorColor, CircleShape)
                )
            }
        },
        modifier = modifier,
        margin = margin,
        userCanScroll = userCanScroll,
    )
}

//[selectIndicatorItem] 被选中的指示器
@Deprecated("因为重组的性能原因,请使用其他同名(重载)函数  For performance reasons of reorganization, use other functions with the same name (overloaded)")
@Composable
fun TextPagerIndicator(
    texts: List<String>,
    offsetPercentWithSelect: Float,
    selectIndex: Int,
    fontSize: TextUnit,
    selectFontSize: TextUnit,
    textColor: Color,
    selectTextColor: Color,
    onIndicatorClick: (index: Int) -> Unit,
    selectIndicatorItem: @Composable PagerIndicatorScope.() -> Unit,
    modifier: Modifier = Modifier,
    margin: Dp = 8.dp,
    userCanScroll: Boolean = true,
) {
    val density = LocalDensity.current
    val fontPx by remember(fontSize) {
        mutableStateOf(density.run { fontSize.toPx() })
    }
    val selectFontPx by remember(selectFontSize) {
        mutableStateOf(density.run { selectFontSize.toPx() })
    }
    PagerIndicator(
        size = texts.size,
        offsetPercentWithSelect = offsetPercentWithSelect,
        selectIndex = selectIndex,
        indicatorItem = { index ->
            Box(modifier = Modifier
                .fillMaxHeight()
                .clickable {
                    if (index != selectIndex)
                        onIndicatorClick(index)
                }) {
                val (size, color) = remember(
                    index,
                    selectIndex,
                    offsetPercentWithSelect,
                    selectFontSize,
                    fontSize,
                    textColor,
                    selectTextColor,
                ) {
                    val percent = abs(selectIndex + offsetPercentWithSelect - index)
                    if (percent > 1f)
                        return@remember fontSize to textColor
                    density.run {
                        percent.getPercentageValue(selectFontPx, fontPx).roundToInt().toSp()
                    } to percent.getPercentageValue(selectTextColor, textColor)
                }
                Text(
                    text = texts[index],
                    fontSize = size,
                    color = color,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        },
        selectIndicatorItem = selectIndicatorItem,
        modifier = modifier,
        margin = margin,
        orientation = Orientation.Horizontal,
        userCanScroll = userCanScroll,
    )
}