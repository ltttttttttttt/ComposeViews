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

import androidx.compose.foundation.gestures.Orientation

/**
 * creator: lt  2022/9/1  lt.dygzs@qq.com
 * effect : Compose中的方位
 *          Position of compose
 * warning:
 */
enum class ComposePosition(val orientation: Orientation) {
    /**
     * 在start的位置
     * Start
     */
    Start(Orientation.Horizontal),

    /**
     * 在end的位置
     * End
     */
    End(Orientation.Horizontal),

    /**
     * 在top的位置
     * Top
     */
    Top(Orientation.Vertical),

    /**
     * 在bottom的位置
     * Bottom
     */
    Bottom(Orientation.Vertical),

    ;

    /**
     * 判断方向是否是横向的
     * Determine if the orientation is horizontal
     */
    fun isHorizontal(): Boolean = orientation == Orientation.Horizontal
}