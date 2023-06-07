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

package com.lt.compose_views.image_viewer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset

/**
 * creator: lt  2023/6/6  lt.dygzs@qq.com
 * effect : [ImageViewer]的状态
 *          State of the [ImageViewer]
 * warning:
 */
@Stable
class ImageViewerState {
    /**
     * 图片的缩放
     * Image zoom
     */
    var zoom by mutableStateOf(1f)

    /**
     * 图片的位移
     * Image offset
     */
    var offset by mutableStateOf(Offset.Zero)

    /**
     * 图片的旋转
     * Image rotation
     */
    var rotation by mutableStateOf(0f)
}

/**
 * 创建一个[remember]的[ImageViewerState]
 * Create the [ImageViewerState] of [remember]
 */
@Composable
fun rememberImageViewerState() = remember { ImageViewerState() }