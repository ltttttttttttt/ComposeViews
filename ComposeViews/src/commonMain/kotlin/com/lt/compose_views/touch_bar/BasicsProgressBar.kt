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

package com.lt.compose_views.touch_bar

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import com.lt.compose_views.util.DragInteractionSource
import com.lt.compose_views.util.midOf
import com.lt.compose_views.util.rememberMutableStateOf

/**
 * creator: lt  2022/10/31  lt.dygzs@qq.com
 * effect : 进度条控件
 *          Progress bar
 * warning:
 * @param progress 当前进度(0f-1f)
 *                 progress
 * @param onProgressChange 进度变化回调
 *                         Callback of the progress change
 * @param modifier 修饰
 * @param orientation 滑动的方向
 *                    Scroll orientation
 * @param userEnable 用户是否可以滑动,等于false时用户滑动无反应,但代码可以执行翻页
 *                   Whether the user can scroll
 * @param onTouchUpEvent 手指抬起事件
 *                       Callback of touch up event
 * @param content compose内容区域
 *                Content of compose
 */
@Composable
fun BasicsProgressBar(
    progress: Float,
    onProgressChange: (progress: Float) -> Unit,
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Horizontal,
    userEnable: Boolean = true,
    onTouchUpEvent: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    var size by rememberMutableStateOf(value = 0)
    val p by rememberUpdatedState(newValue = progress)
    val state = remember(size, onProgressChange) {
        ScrollableState {
            onProgressChange(
                midOf(
                    0f,
                    p + it / size,
                    1f
                )
            )
            it
        }
    }
    Box(
        modifier
            .layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)
                size =
                    if (orientation == Orientation.Horizontal) placeable.width else placeable.height
                layout(placeable.width, placeable.height) {
                    placeable.placeRelative(0, 0)
                }
            }
            .scrollable(
                state,
                orientation,
                userEnable,
                interactionSource = remember(onTouchUpEvent) {
                    if (onTouchUpEvent == null) null else DragInteractionSource {
                        if (it is DragInteraction.Stop || it is DragInteraction.Cancel)
                            onTouchUpEvent()
                    }
                })
    ) {
        content()
    }
}