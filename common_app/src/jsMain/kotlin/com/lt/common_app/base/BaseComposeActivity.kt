package com.lt.common_app.base

import androidx.compose.runtime.*
import com.lt.common_app.ComposePagerActivity
import com.lt.common_app.DateSelectorA
import com.lt.common_app.MainActivity
import com.lt.common_app.MainListActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.math.abs
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
        val constructor = this::class.js.asDynamic().prototype.constructor
        val a = constructor.call() as BaseComposeActivity
//        val a= DateSelectorA()
//        val a = js("new ${clazz.simpleName!!}()") as BaseComposeActivity
//        val a=   kotlinx.browser.window[clazz.simpleName!!].invoke() as BaseComposeActivity
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

    actual open fun mOnCreate() {
    }
}