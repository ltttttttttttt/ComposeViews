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

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * creator: lt  2022/12/3  lt.dygzs@qq.com
 * effect : ValueSelect的状态
 *          State of the [ValueSelect]
 * warning:
 */
class ValueSelectState {
    lateinit var lazyListState: LazyListState
        internal set
}

/**
 * 创建一个[remember]的[ValueSelectState]
 * Create the [ValueSelectState] of [remember]
 */
@Composable
fun rememberValueSelectState() = remember { ValueSelectState() }