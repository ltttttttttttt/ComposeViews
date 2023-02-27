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

import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * creator: lt  2022/9/26  lt.dygzs@qq.com
 * effect : 监听[DragInteraction]的状态变化
 *          Listen the [DragInteraction]
 * warning:
 */
class DragInteractionSource(
    private val dragInteractionChange: (DragInteraction) -> Unit
) : MutableInteractionSource {
    override val interactions: Flow<Interaction>
        get() = flowOf()

    override suspend fun emit(interaction: Interaction) {
        (interaction as? DragInteraction)?.let(dragInteractionChange)
    }

    override fun tryEmit(interaction: Interaction): Boolean {
        (interaction as? DragInteraction)?.let(dragInteractionChange)
        return true
    }
}