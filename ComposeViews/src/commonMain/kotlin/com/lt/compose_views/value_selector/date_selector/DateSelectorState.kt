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

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.lt.compose_views.value_selector.ValueSelectState

/**
 * creator: lt  2022/12/7  lt.dygzs@qq.com
 * effect : DateSelector的状态
 *          State of the [DateSelector]
 * warning:
 * @param defaultXxx 默认选中的日期
 *                   Default selected date
 * @param minXxx 最小可选择日期
 *                Minimum selected date
 * @param maxXxx 最大可选择日期
 *                Maximum selected date
 */
@Stable
class DateSelectorState(
    internal val defaultYear: Int,
    internal val defaultMonth: Int,
    internal val defaultDay: Int,
    minYear: Int = 1900,
    maxYear: Int = 2100,
) {
    val yearState = ValueSelectState()
    val monthState = ValueSelectState()
    val dayState = ValueSelectState()

    internal var years by mutableStateOf(ArrayList((minYear..maxYear).map { it.toString() }))
    internal var months by mutableStateOf(ArrayList((1..12).map { it.toString() }))
    internal var days by mutableStateOf(ArrayList((1..31).map { it.toString() }))

    fun getYear(): String = years[yearState.getSelectIndex()]
    fun getMonth(): String = months[monthState.getSelectIndex()]
    fun getDay(): String = days[dayState.getSelectIndex()]
}