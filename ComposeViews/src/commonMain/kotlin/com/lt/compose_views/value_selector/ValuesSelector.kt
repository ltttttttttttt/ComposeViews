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

package com.lt.compose_views.value_selector

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lt.compose_views.other.VerticalSpace

/**
 * creator: lt  2022/12/7  lt.dygzs@qq.com
 * effect : 值选择器列表
 *          Values Selector
 * warning:
 * @param valuesList 数据列表
 *                   Values
 * @param states 状态列表
 *               State list
 * @param modifier 修饰
 * @param isLoop 值列表是否可循环
 *               Whether the value list is loop
 * @param defaultSelectIndexList 默认选中的值索引列表
 *                               Default selected value index list
 */
@ExperimentalFoundationApi
@Composable
fun ValuesSelector(
    valuesList: List<ArrayList<String>>,
    states: List<ValueSelectState>,
    modifier: Modifier = Modifier,
    isLoop: Boolean = false,
    defaultSelectIndexList: List<Int> = remember(valuesList.size) {
        valuesList.indices.map { 0 }
    },
) {
    Box(modifier) {
        Row {
            repeat(valuesList.size) {
                ValueSelector(
                    values = remember(valuesList, it) { valuesList[it] },
                    state = remember(states, it) { states[it] },
                    modifier = Modifier.weight(1f),
                    defaultSelectIndex = remember(defaultSelectIndexList, it) { defaultSelectIndexList[it] },
                    isLoop = isLoop,
                )
            }
        }
        CenterLines()
    }
}

//中间的两条线
@Composable
internal fun BoxScope.CenterLines() {
    Column(Modifier.fillMaxWidth().align(Alignment.Center)) {
        Divider(Modifier.fillMaxWidth().height(1.dp), lineColor)
        VerticalSpace(48)
        Divider(Modifier.fillMaxWidth().height(1.dp), lineColor)
    }
}

internal val lineColor = Color(0xffe6e6e6)