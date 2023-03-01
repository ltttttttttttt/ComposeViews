package com.lt.common_app.base

import androidx.compose.runtime.*
import com.lt.common_app.MainActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.reflect.KClass

/**
 * creator: lt  2021/5/23  lt.dygzs@qq.com
 * effect :
 * warning:
 */
actual abstract class BaseComposeActivity {
    actual val mainScope = MainScope()

    actual open fun getTitleText(): String {
        return this::class.simpleName!!.replace("Activity", "")
    }

    init {
        mainScope.launch {
            mOnCreate()
        }
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
//        _activityStack.add(clazz.)
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

    actual open fun mOnCreate() {
    }
}