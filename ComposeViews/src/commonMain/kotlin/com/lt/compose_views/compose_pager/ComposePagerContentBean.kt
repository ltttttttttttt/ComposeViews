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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier

/**
 * creator: lt  2022/9/8  lt.dygzs@qq.com
 * effect :
 * warning:
 */
@Stable
internal data class ComposePagerContentBean(
    val key:Int,
    val paramModifier: Modifier,
    val paramScope: ComposePagerScope,
    val function: @Composable (Modifier, ComposePagerScope) -> Unit
)