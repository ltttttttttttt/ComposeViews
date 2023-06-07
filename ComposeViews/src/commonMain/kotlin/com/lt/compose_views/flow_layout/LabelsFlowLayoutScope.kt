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

import androidx.compose.runtime.Stable

/**
 * creator: lt  2022/10/26  lt.dygzs@qq.com
 * effect : LabelsFlowLayout的compose作用域
 *          Compose scope of the [LabelsFlowLayout]
 * warning:
 */
@Stable
class LabelsFlowLayoutScope(
    private val state: LabelsFlowLayoutState,
    val index: Int,
) {
    /**
     * 获取当前索引的选中状态
     * Get whether the current index is selected
     */
    fun getIsSelect(): Boolean = state.getIsSelect(index)

    /**
     * 设置选中状态
     * Set whether the index is selected
     * @param onExceededLimit 多选时,如果超出了选择上限时回调
     *                        Exceeded limit when multiple selection
     */
    fun setIsSelect(isSelect: Boolean, onExceededLimit: () -> Unit) =
        state.setIsSelect(index, isSelect, onExceededLimit)
}