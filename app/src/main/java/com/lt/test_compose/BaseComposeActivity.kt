package com.lt.test_compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.lt.test_compose.ui.theme.MyTheme

/**
 * creator: lt  2021/5/23  lt.dygzs@qq.com
 * effect :
 * warning:
 */
abstract class BaseComposeActivity : AppCompatActivity() {

    @Composable
    abstract fun InitCompose()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                InitCompose()
            }
        }
    }
}