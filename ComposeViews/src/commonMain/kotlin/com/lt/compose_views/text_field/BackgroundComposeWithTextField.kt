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

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.lt.compose_views.util.ColorF5

/**
 * creator: lt  2022/7/16  lt.dygzs@qq.com
 * effect : [GoodTextField]的背景
 *          Background of the [GoodTextField]
 * warning:
 */
interface BackgroundComposeWithTextField {
    /**
     * 通过[Modifier]来设置[GoodTextField]的背景
     * Set the background of the [GoodTextField] through [Modifier]
     */
    @Composable
    fun setBackground(modifier: Modifier): Modifier

    companion object {
        /**
         * 通过[Shape]和[Color]设置背景
         * Set the background through [Shape] and [Color]
         */
        fun createBackgroundCompose(shape: Shape, color: Color) =
            object : BackgroundComposeWithTextField {
                @Composable
                override fun setBackground(modifier: Modifier): Modifier {
                    return modifier.background(color, shape)
                }
            }

        /**
         * 默认[GoodTextField]的背景,灰色圆角矩形
         * Default [GoodTextField] background, gray rounded rectangle
         */
        val DEFAULT = createBackgroundCompose(RoundedCornerShape(8.dp), ColorF5)
    }
}