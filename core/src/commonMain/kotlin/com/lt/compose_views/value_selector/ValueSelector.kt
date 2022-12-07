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

package com.lt.compose_views.value_selector

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.compose_views.other.VerticalSpace
import com.lt.compose_views.util.Color333
import kotlinx.coroutines.launch

/**
 * creator: lt  2022/12/3  lt.dygzs@qq.com
 * effect : 值选择器
 *          Value selector
 * warning:
 * @param values 值列表(值列表不支持重复值)
 *               Value list(Value list does not support duplicate values)
 * @param state ValueSelector的状态
 *              ValueSelector's state
 * @param modifier 修饰
 * @param defaultSelectIndex 默认选中的值索引
 *                           Default selected value index
 * @param isLoop 值列表是否可循环
 *               Whether the value list is loop
 * @param textSize1 未选中的字体大小(第一排和第五排)
 *                  Text size(line 1 and 5)
 * @param textSize2 未选中的字体大小(第二排和第四排)
 *                  Text size(line 2 and 4)
 * @param selectedTextSize 选中的字体大小
 *                         Text size with selected
 * @param textColor1 未选中的字体颜色(第一排和第五排)
 *                   Text color(line 1 and 5)
 * @param textColor2 未选中的字体颜色(第二排和第四排)
 *                   Text color(line 2 and 4)
 * @param selectedTextColor 选中的字体颜色
 *                          Text color with selected
 */
@ExperimentalFoundationApi
@Composable
fun ValueSelector(
    values: List<String>,
    state: ValueSelectState,
    modifier: Modifier = Modifier,
    defaultSelectIndex: Int = 0,
    isLoop: Boolean = false,
    textSize1: TextUnit = defaultTextSize1,
    textSize2: TextUnit = defaultTextSize2,
    selectedTextSize: TextUnit = defaultSelectedTextSize,
    textColor1: Color = defaultTextColor,
    textColor2: Color = defaultTextColor,
    selectedTextColor: Color = defaultSelectedTextColor,
) {
    remember(defaultSelectIndex, state, values) {
        state.lazyListState = LazyListState(
            if (isLoop)
                values.size * loopMultiple / 2 + defaultSelectIndex
            else
                defaultSelectIndex
        )
    }
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    val itemHeight = remember(density) { density.run { 50.dp.toPx() } }
    val scrollStopListener = remember {
        object : NestedScrollConnection {
            override suspend fun onPreFling(available: Velocity): Velocity {
                //计算速度大概能滚动多少条目,并执行滚动动画
                val itemNum = Math.round(Math.abs(available.y.toDouble()) / 4 / itemHeight).toInt()
                coroutineScope.launch {
                    if (available.y > 0) {
                        state.lazyListState.animateScrollToItem(
                            maxOf(
                                0,
                                state.lazyListState.firstVisibleItemIndex - itemNum
                            )
                        )
                    } else {
                        state.lazyListState.animateScrollToItem(
                            minOf(
                                values.size * loopMultiple,
                                state.lazyListState.firstVisibleItemIndex + itemNum
                            )
                        )
                    }
                }
                return available
            }
        }
    }
    Box(
        modifier.height(205.dp)
            .fillMaxWidth()
            .nestedScroll(scrollStopListener)
    ) {
        LazyColumn(state = state.lazyListState, modifier = Modifier.fillMaxSize()) {
            val itemFun: @Composable (index: Int, value: String) -> Unit = { index, value ->
                val textAttributes by remember(state.lazyListState.firstVisibleItemIndex) {
                    when (state.lazyListState.firstVisibleItemIndex) {
                        index -> mutableStateOf(selectedTextSize to selectedTextColor)
                        index - 1, index + 1 -> mutableStateOf(textSize2 to textColor2)
                        index - 2, index + 2 -> mutableStateOf(textSize1 to textColor1)
                        else -> mutableStateOf(textSize1 to textColor1)
                    }
                }
                Box(Modifier.fillMaxWidth().height(itemHeightDp)) {
                    Text(
                        value,
                        Modifier.align(Alignment.Center),
                        fontSize = textAttributes.first,
                        color = textAttributes.second,
                    )
                }
            }
            if (isLoop) {
                val valueSize = values.size
                items(valueSize * loopMultiple, key = { it }) {
                    itemFun(it - 2, remember(it) { values[it % valueSize] })
                }
            } else {
                item {
                    VerticalSpace(itemHeightDp)
                }
                item {
                    VerticalSpace(itemHeightDp)
                }
                itemsIndexed(values, key = { index, it -> it }) { index, value ->
                    itemFun(index, value)
                }
                item {
                    VerticalSpace(itemHeightDp)
                }
                item {
                    VerticalSpace(itemHeightDp)
                }
            }
        }
        Column(Modifier.fillMaxWidth().align(Alignment.Center)) {
            Divider(Modifier.fillMaxWidth().height(1.dp), lineColor)
            VerticalSpace(48)
            Divider(Modifier.fillMaxWidth().height(1.dp), lineColor)
        }
    }
}

private val defaultTextSize1 = 14.sp
private val defaultTextSize2 = 16.sp
private val defaultSelectedTextSize = 18.sp
private val defaultTextColor = Color333
private val defaultSelectedTextColor = Color(0xff0D8AFF)
private val lineColor = Color(0xffe6e6e6)
private val loopMultiple = 10000
private val itemHeightDp = 41.dp