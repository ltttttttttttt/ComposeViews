package com.lt.test_compose.base

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
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
}