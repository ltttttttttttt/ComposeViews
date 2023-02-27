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

package com.lt.compose_views.value_selector.date_selector

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import com.lt.compose_views.value_selector.*
import com.lt.compose_views.value_selector.CenterLines
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * creator: lt  2022/12/7  lt.dygzs@qq.com
 * effect : 日期选择器
 *          Date Selector
 * warning:
 * @param state DateSelector的状态
 *              DateSelector's state
 * @param modifier 修饰
 * @param isLoop 值列表是否可循环
 *               Whether the value list is loop
 */
@ExperimentalFoundationApi
@Composable
fun DateSelector(
    state: DateSelectorState,
    modifier: Modifier = Modifier,
    isLoop: Boolean = false,
    cacheSize: Int = 2,
    textSizes: ArrayList<TextUnit> = remember { arrayListOf(valueSelector_defaultTextSize2, valueSelector_defaultTextSize1) },
    selectedTextSize: TextUnit = valueSelector_defaultSelectedTextSize,
    textColors: ArrayList<Color> = remember { arrayListOf(valueSelector_defaultTextColor, valueSelector_defaultTextColor) },
    selectedTextColor: Color = valueSelector_defaultSelectedTextColor,
) {
    LaunchedEffect(Unit) {
        //处理每月天数变化
        snapshotFlow {
            when (state.getMonth()) {
                "1", "3", "5", "7", "8", "10", "12" -> 31
                "2" -> {
                    //闰年计算
                    val year = state.getYear().toInt()
                    if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) 29 else 28
                }

                else -> 30
            }
        }.distinctUntilChanged()
            .collect {
                state.days = ArrayList((1..it).map { it.toString() })
            }
    }
    Box(modifier) {
        Row {
            ValueSelector(
                values = state.years,
                state = state.yearState,
                modifier = Modifier.weight(1f),
                defaultSelectIndex = remember(state) {
                    state.years.indexOf(state.defaultYear.toString())
                },
                isLoop = isLoop,
                cacheSize = cacheSize,
                textSizes = textSizes,
                selectedTextSize = selectedTextSize,
                textColors = textColors,
                selectedTextColor = selectedTextColor,
            )
            ValueSelector(
                values = state.months,
                state = state.monthState,
                modifier = Modifier.weight(1f),
                defaultSelectIndex = remember(state) {
                    state.months.indexOf(state.defaultMonth.toString())
                },
                isLoop = isLoop,
                cacheSize = cacheSize,
                textSizes = textSizes,
                selectedTextSize = selectedTextSize,
                textColors = textColors,
                selectedTextColor = selectedTextColor,
            )
            ValueSelector(
                values = state.days,
                state = state.dayState,
                modifier = Modifier.weight(1f),
                defaultSelectIndex = remember(state) {
                    state.days.indexOf(state.defaultDay.toString())
                },
                isLoop = isLoop,
                cacheSize = cacheSize,
                textSizes = textSizes,
                selectedTextSize = selectedTextSize,
                textColors = textColors,
                selectedTextColor = selectedTextColor,
            )
        }
        CenterLines()
    }
}