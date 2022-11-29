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

package com.lt.common_app.base

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope

/**
 * creator: lt  2021/5/23  lt.dygzs@qq.com
 * effect :
 * warning:
 */
expect abstract class BaseComposeActivity() {
    val mainScope: CoroutineScope

    open fun getTitleText(): String

    open fun mOnCreate()

    @Composable
    actual abstract fun ComposeContent()

    fun mFinish()

    fun String.showToast()

    fun jump(clazz: Class<out BaseComposeActivity>)
}