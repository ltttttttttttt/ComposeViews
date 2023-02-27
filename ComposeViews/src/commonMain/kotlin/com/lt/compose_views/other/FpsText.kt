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

package com.lt.compose_views.other

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.lt.compose_views.util.rememberMutableStateOf

/**
 * 测量compose帧数
 * 参考: https://juejin.cn/post/7090554700344328229
 */
@Composable
@Deprecated("建议仅测试时使用")
fun FpsText(modifier: Modifier = Modifier) {
    var fpsCount by rememberMutableStateOf(0)
    var fps by rememberMutableStateOf(0)
    var lastUpdate by rememberMutableStateOf(0L)
    Text(
        text = "Fps: $fps",
        modifier = modifier,
        color = Color.Red,
        style = MaterialTheme.typography.body1
    )

    LaunchedEffect(Unit) {
        while (true) {
            withFrameMillis { ms ->
                fpsCount++
                if (fpsCount == 5) {
                    fps = (5000 / (ms - lastUpdate)).toInt()
                    lastUpdate = ms
                    fpsCount = 0
                }
            }
        }
    }
}