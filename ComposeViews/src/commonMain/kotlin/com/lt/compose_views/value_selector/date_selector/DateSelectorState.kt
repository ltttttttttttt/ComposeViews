package com.lt.compose_views.value_selector.date_selector

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