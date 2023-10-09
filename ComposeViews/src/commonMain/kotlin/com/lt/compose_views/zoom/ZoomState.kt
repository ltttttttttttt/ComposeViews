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

package com.lt.compose_views.zoom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset

/**
 * creator: lt  2023/6/6  lt.dygzs@qq.com
 * effect : [ZoomLayout]的状态
 *          State of the [ZoomLayout]
 * warning:
 */
@Stable
class ZoomState {
    /**
     * 缩放
     * Zoom
     */
    var zoom by mutableStateOf(1f)

    /**
     * 位移
     * Offset
     */
    var offset by mutableStateOf(Offset.Zero)

    /**
     * 旋转
     * Rotation
     */
    var rotation by mutableStateOf(0f)
}

/**
 * 创建一个[remember]的[ZoomState]
 * Create the [ZoomState] of [remember]
 */
@Composable
fun rememberZoomState() = remember { ZoomState() }