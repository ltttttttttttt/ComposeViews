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

package com.lt.compose_views.text_field

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import com.lt.compose_views.util.Color999

/**
 * creator: lt  2022/7/16  lt.dygzs@qq.com
 * effect : 用于显示输入框没有内容时的组件
 *          Content of the [TextField] with if value is Empty
 * warning:
 */
interface HintComposeWithTextField {
    /**
     * 输入框没有内容时展示
     * Content of the [TextField] with if value is Empty
     */
    @Composable
    fun Hint(fontSize: TextUnit)

    companion object {
        /**
         * 创建'输入框没有内容时展示一段文字组件'的方法
         * Text content of the [TextField] with if value is Empty
         */
        fun createTextHintCompose(hint: String) = object : HintComposeWithTextField {
            @Composable
            override fun Hint(fontSize: TextUnit) {
                Text(
                    text = hint,
                    color = Color999,
                    fontSize = fontSize
                )
            }
        }
    }
}