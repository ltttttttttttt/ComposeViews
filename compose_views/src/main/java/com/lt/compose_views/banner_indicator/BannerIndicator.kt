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

package com.lt.compose_views.banner_indicator

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * creator: lt  2022/6/27  lt.dygzs@qq.com
 * effect : 适用于Banner的指示器
 * warning:
 * [size]指示器数量
 * [offsetPercentWithSelect]选中的指示器的偏移百分比
 * [selectIndex]选中的索引
 * [indicatorItem]未被选中的指示器
 * [selectIndicatorItem]被选中的指示器
 * [modifier]修饰
 * [margin]指示器之间的间距
 */
@Composable
fun HorizontalIndicator(
    size: Int,
    offsetPercentWithSelect: Float,
    selectIndex: Int,
    indicatorItem: @Composable (index: Int) -> Unit,
    selectIndicatorItem: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    margin: Dp = 8.dp
) {
    // TODO by lt 2022/9/5 22:33 待实现
    Box(modifier, contentAlignment = Alignment.CenterStart) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(size) {
                if (it != 0)
                    Spacer(modifier = Modifier.width(margin))
                indicatorItem(it)
            }
        }
        Box(Modifier.padding(start = (40.dp + margin) * selectIndex)) {// TODO by lt test
            selectIndicatorItem()
        }
    }
}