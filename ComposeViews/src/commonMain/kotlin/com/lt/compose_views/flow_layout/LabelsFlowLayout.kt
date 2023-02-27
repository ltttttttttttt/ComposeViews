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

package com.lt.compose_views.flow_layout

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lt.compose_views.util.SelectMode

/**
 * creator: lt  2022/10/26  lt.dygzs@qq.com
 * effect : 可以自动确定选中状态的[FlowLayout]
 *          [FlowLayout] that can automatically determine the selected state
 * warning:
 * @param size label的数量
 *             Number of labels
 * @param modifier 修饰
 * @param selectMode 选择模式
 *                   Select mode
 * @param state [LabelsFlowLayout]的状态
 *              State of the [LabelsFlowLayout]
 * @param orientation 排列的方向,[Orientation.Horizontal]时会先横向排列,一排放不下会换到下一行继续横向排列
 *                    Direction of arrangement
 * @param horizontalAlignment 子级在横向上的位置
 *                            Alignment of horizontal
 * @param verticalAlignment 子级在竖向上的位置
 *                          Alignment of vertical
 * @param horizontalMargin 子级与子级在横向上的间距
 *                          Margin of horizontal
 * @param verticalMargin 子级与子级在竖向上的间距
 *                       Margin of vertical
 * @param maxLines 最多能放多少行(或列)
 *                 How many lines can be placed
 * @param content compose内容区域
 *                Content of compose
 */
@Composable
fun LabelsFlowLayout(
    size: Int,
    modifier: Modifier = Modifier,
    selectMode: SelectMode = SelectMode.Radio,
    state: LabelsFlowLayoutState = rememberLabelsFlowLayoutState(size, selectMode),
    orientation: Orientation = Orientation.Horizontal,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    horizontalMargin: Dp = 0.dp,
    verticalMargin: Dp = 0.dp,
    maxLines: Int = Int.MAX_VALUE,
    content: @Composable LabelsFlowLayoutScope.() -> Unit
) {
    val scopes = remember(size, state) {
        Array(size) {
            LabelsFlowLayoutScope(state, it)
        }
    }
    FlowLayout(
        modifier,
        orientation,
        horizontalAlignment,
        verticalAlignment,
        horizontalMargin,
        verticalMargin,
        maxLines,
    ) {
        scopes.forEach {
            it.content()
        }
    }
}
