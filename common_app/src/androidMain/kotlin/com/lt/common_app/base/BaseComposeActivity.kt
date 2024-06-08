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

package com.lt.common_app.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlin.reflect.KClass

/**
 * creator: lt  2021/5/23  lt.dygzs@qq.com
 * effect :
 * warning:
 */
@Immutable
actual abstract class BaseComposeActivity : AppCompatActivity() {
    actual val mainScope = MainScope()

    actual open fun getTitleText(): String {
        return this::class.java.simpleName.replace("Activity", "")
    }

    @Composable
    actual abstract fun ComposeContent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getTitleText()
        mOnCreate()
        setContent {
            MyTheme {
                ComposeContent()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

    actual fun mFinish() {
        finish()
    }

    actual fun String.showToast() {
        Toast.makeText(this@BaseComposeActivity, this, Toast.LENGTH_SHORT).show()
    }

    actual fun jump(clazz: KClass<out BaseComposeActivity>) {
        startActivity(Intent(this, clazz.java))
    }

    actual open fun mOnCreate() {
    }
}