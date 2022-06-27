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
import kotlinx.coroutines.MainScope

/**
 * creator: lt  2021/5/23  lt.dygzs@qq.com
 * effect :
 * warning:
 */
abstract class BaseComposeActivity : AppCompatActivity() {
    val mainScope = MainScope()

    @Composable
    abstract fun ComposeContent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        var fpsCount by remember { mutableStateOf(0) }
        var fps by remember { mutableStateOf(0) }
        var lastUpdate by remember { mutableStateOf(0L) }
        Text(
            text = "Fps: $fps", modifier = modifier
                .size(60.dp), color = Color.Red, style = MaterialTheme.typography.body1
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