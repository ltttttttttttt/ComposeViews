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

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.compose_views.util.Color333

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
 * @param cacheSize 上下展示多少个额外的值
 *                  How many additional values are displayed up and down
 * @param textSize 未选中的字体大小
 *                 Text size
 * @param selectedTextSize 选中的字体大小
 *                         Text size with selected
 * @param textColor 未选中的字体颜色
 *                  Text color
 * @param selectedTextColor 选中的字体颜色
 *                          Text color with selected
 */
@Composable
fun ValueSelector(
    values: List<String>,
    state: ValueSelectState,
    modifier: Modifier = Modifier,
    defaultSelectIndex: Int = 0,
    isLoop: Boolean = false,
    cacheSize: Int = 2,
    textSize: TextUnit = defaultTextSize,
    selectedTextSize: TextUnit = defaultSelectedTextSize,
    textColor: Color = defaultTextColor,
    selectedTextColor: Color = defaultSelectedTextColor,
) {
    remember(defaultSelectIndex, state) {
        state.lazyListState = LazyListState(defaultSelectIndex)
    }
    LazyColumn(state = state.lazyListState, modifier = modifier) {
        // TODO by lt 2022/12/3 23:14 怎么搞成只显示n条目的高度
        if (isLoop) {
            // TODO by lt 2022/12/3 23:12 使用item(size)的形式,size为values的n倍
        } else {
            // TODO by lt 2022/12/3 23:11 上下加入cacheSize个空白条目
            items(values, key = { it }) { value ->
                // TODO by lt 2022/12/3 23:16 ui调整,选中状态观察和不同的ui调整
                Text(
                    value,
                    Modifier.padding(vertical = 10.dp),
                    textAlign = TextAlign.Center,
                    fontSize = textSize,
                    color = textColor,
                )
            }
        }
    }
}

// TODO by lt 2022/12/3 23:21
private val defaultTextSize = 14.sp
private val defaultSelectedTextSize = 16.sp
private val defaultTextColor = Color333
private val defaultSelectedTextColor = Color.Blue