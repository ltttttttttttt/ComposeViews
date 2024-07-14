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

package com.lt.compose_views.compose_pager

import androidx.compose.runtime.Stable

/**
 * ComposePager的compose作用域
 * Compose scope of the [ComposePager]
 */
@Stable
class ComposePagerScope(
    /**
     * 当前的ComposePager布局content所在的索引
     * Current content index in the [ComposePager]
     */
    val index: Int,

    //真实索引
    internal val realIndex: Int,
)