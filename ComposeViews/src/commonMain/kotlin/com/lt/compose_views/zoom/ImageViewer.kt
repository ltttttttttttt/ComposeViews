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

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import com.lt.compose_views.util.midOf
import kotlin.math.roundToInt

/**
 * creator: lt  2023/6/6  lt.dygzs@qq.com
 * effect : 图片预览
 *          Image viewer
 * warning:
 * @param painter 要绘制的图像
 *                Painter to draw
 * @param modifier 修饰
 * @param zoomState [ZoomLayout]的状态
 *                   ZoomLayout's state
 * @param userCanRotation 用户是否可以旋转
 *                        Whether the user can rotate
 */
@Composable
fun ImageViewer(
    painter: Painter,
    modifier: Modifier = Modifier,
    zoomState: ZoomState = rememberZoomState(),
    userCanRotation: Boolean = false,
) {
    Box(modifier.clipToBounds()
        .pointerInput(zoomState) {
            //监听位移,缩放和旋转手势
            detectTransformGestures(true) { centroid, pan, zoom, rotation ->
                val newZoom = zoomState.zoom * zoom
                zoomState.zoom = midOf(0.25f, newZoom, 4f)
                zoomState.offset += (pan / zoomState.zoom)
                if (userCanRotation) {
                    zoomState.rotation += rotation
                }
            }
        }.pointerInput(zoomState) {
            //双击时设置缩放大小
            detectTapGestures(onDoubleTap = {
                zoomState.zoom = when {
                    zoomState.zoom < 1f || zoomState.zoom == 4f -> {
                        zoomState.offset = Offset.Zero
                        1f
                    }

                    zoomState.zoom in 1f..2.49f -> 2.5f
                    else -> 4f
                }
            })
        }
    ) {
        Image(
            painter,
            "Image viewer",
            modifier = Modifier.fillMaxSize()
                .let {
                    if (userCanRotation)
                        it.rotate(zoomState.rotation)
                    else
                        it
                }
                .scale(zoomState.zoom)
                .offset {
                    IntOffset(
                        zoomState.offset.x.roundToInt(),
                        zoomState.offset.y.roundToInt()
                    )
                }
        )
    }
}