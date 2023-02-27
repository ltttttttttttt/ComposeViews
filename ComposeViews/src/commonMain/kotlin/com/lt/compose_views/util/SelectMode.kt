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

package com.lt.compose_views.util

/**
 * creator: lt  2022/10/26  lt.dygzs@qq.com
 * effect : 选择模式
 *          Select mode
 * warning:
 */
sealed interface SelectMode {
    /**
     * 单选
     * radio
     */
    object Radio : SelectMode

    /**
     * 多选
     * Multiple choice
     */
    class MultipleChoice(val max: Int = Int.MAX_VALUE) : SelectMode
}