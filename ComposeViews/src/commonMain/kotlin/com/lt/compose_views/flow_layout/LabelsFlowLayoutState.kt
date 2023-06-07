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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.lt.compose_views.util.SelectMode

/**
 * creator: lt  2022/10/26  lt.dygzs@qq.com
 * effect : [LabelsFlowLayout]的状态
 *          State of the [LabelsFlowLayout]
 * warning:
 */
@Stable
class LabelsFlowLayoutState {

    constructor(size: Int, selectMode: SelectMode) {
        this.selectMode = selectMode
        repeat(size) {
            selectData.add(false)
        }
    }

    constructor(data: Collection<Boolean>, selectMode: SelectMode) {
        this.selectMode = selectMode
        selectData.addAll(data)
    }

    /**
     * 选中数据的状态
     * State of select
     */
    val selectData = mutableStateListOf<Boolean>()

    /**
     * 选择模式
     * Select mode
     */
    var selectMode: SelectMode

    /**
     * 获取选中状态
     * Get whether the index is selected
     */
    fun getIsSelect(index: Int): Boolean = selectData[index]

    /**
     * 设置选中状态
     * Set whether the index is selected
     * @param onExceededLimit 多选时,如果超出了选择上限时回调
     *                        Exceeded limit when multiple selection
     */
    fun setIsSelect(index: Int, isSelect: Boolean, onExceededLimit: () -> Unit) {
        //如果是设置为不选中,则不需要判断上限
        if (!isSelect) {
            selectData[index] = false
            return
        }
        //isSelect == true
        if (getIsSelect(index))
            return
        when (val selectMode = selectMode) {
            is SelectMode.Radio -> {//单选
                for (i in 0 until selectData.size) {
                    if (getIsSelect(i)) {
                        selectData[i] = false
                        break
                    }
                }
                selectData[index] = true
            }
            is SelectMode.MultipleChoice -> {//多选
                if (selectMode.max >= selectData.size) {
                    selectData[index] = true
                    return
                }
                if (selectData.sumOf { (if (it) 1 else 0).toInt() } >= selectMode.max) {
                    onExceededLimit()
                } else {
                    selectData[index] = true
                }
            }
        }
    }
}

/**
 * 创建一个[remember]的[LabelsFlowLayoutState]
 * Create the [LabelsFlowLayoutState] of [remember]
 */
@Composable
fun rememberLabelsFlowLayoutState(size: Int, selectMode: SelectMode) =
    remember(size, selectMode) { LabelsFlowLayoutState(size, selectMode) }