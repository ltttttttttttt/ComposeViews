/*
 * Copyright lt 2022
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

package com.lt.test_compose.base

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lt.compose_views.util.rememberMutableStateOf
import kotlinx.coroutines.MainScope

/**
 * creator: lt  2021/5/23  lt.dygzs@qq.com
 * effect :
 * warning:
 */
abstract class BaseComposeActivity : AppCompatActivity() {
    val mainScope = MainScope()

    open fun getTitleText(): String {
        return this::class.java.simpleName.replace("Activity", "")
    }

    @Composable
    abstract fun ComposeContent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getTitleText()
        setContent {
            MyTheme {
                ComposeContent()
            }
        }
    }

    /**
     * 测量compose帧数
     */
    @Composable
    fun FpsMonitor(modifier: Modifier) {
        var fpsCount by rememberMutableStateOf(0)
        var fps by rememberMutableStateOf(0)
        var lastUpdate by rememberMutableStateOf(0L)
        Text(
            text = "Fps: $fps", modifier = modifier
                .size(60.dp, 30.dp), color = Color.Red, style = MaterialTheme.typography.body1
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
}