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

import androidx.compose.runtime.*
import com.lt.common_app.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import newInstance
import kotlin.reflect.KClass

/**
 * creator: lt  2021/5/23  lt.dygzs@qq.com
 * effect :
 * warning:
 */
@Immutable
actual abstract class BaseComposeActivity actual constructor() {
    actual val mainScope = MainScope()

    actual open fun getTitleText(): String {
        return this::class.simpleName!!.replace("Activity", "")
    }

    init {
        mainScope.launch {
            mOnCreate()
        }
    }

    actual open fun mOnCreate() {
    }

    @Composable
    actual abstract fun ComposeContent()

    actual fun mFinish() {
        _activityStack.removeLast()
        mainScope.cancel()
    }

    actual fun String.showToast() {
        _toast = this
        _toastIsShow = true
        _toastChannel.trySend(Unit)
    }

    actual fun jump(clazz: KClass<out BaseComposeActivity>) {
        val a = clazz.newInstance()
        _activityStack.add(a)
    }

    companion object {
        val _activityStack = mutableStateListOf<BaseComposeActivity>(MainActivity())
        var _toast by mutableStateOf("")
        var _toastIsShow by mutableStateOf(false)
        val _toastChannel = Channel<Unit>()

        init {
            flow {
                for (unit in _toastChannel) {
                    emit(unit)
                }
            }.debounce(3000)
                .onEach { _toastIsShow = false }
                .launchIn(GlobalScope)
        }
    }
}