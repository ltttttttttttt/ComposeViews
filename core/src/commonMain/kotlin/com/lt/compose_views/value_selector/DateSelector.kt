package com.lt.compose_views.value_selector

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

/**
 * creator: lt  2022/12/7  lt.dygzs@qq.com
 * effect : 日期选择器
 *          Date Selector
 * warning:
 * @param years 年份列表
 *              years
 * @param modifier 修饰
 * @param isLoop 值列表是否可循环
 *               Whether the value list is loop
 * @param defaultTimeMillis 默认选中的时间戳
 *                          Default selected time millis
 */
@ExperimentalFoundationApi
@Composable
fun DateSelector(
    years: ArrayList<String>,
    modifier: Modifier = Modifier,
    isLoop: Boolean = false,
    defaultTimeMillis: Long = remember { System.currentTimeMillis() }
) {
    val yearState = rememberValueSelectState()
    val monthState = rememberValueSelectState()
    val dayState = rememberValueSelectState()
    // TODO by lt 2022/12/7 15:54 默认选择,根据月份重新设置日的数据,获取当前选择的数据(增加datestate)
    Box(modifier) {
        Row {
            ValueSelector(
                values = years,
                state = yearState,
                modifier = Modifier.weight(1f),
                defaultSelectIndex = 0,// TODO by lt 2022/12/7 18:02 defaultTimeMillis
                isLoop = isLoop,
            )
            ValueSelector(
                values = remember { arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12") },
                state = monthState,
                modifier = Modifier.weight(1f),
                defaultSelectIndex = 0,// TODO by lt 2022/12/7 18:02 defaultTimeMillis
                isLoop = isLoop,
            )
            ValueSelector(
                values = years,// TODO by lt 2022/12/7 18:04  
                state = dayState,
                modifier = Modifier.weight(1f),
                defaultSelectIndex = 0,// TODO by lt 2022/12/7 18:02 defaultTimeMillis
                isLoop = isLoop,
            )
        }
        CenterLines()
    }
}