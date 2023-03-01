package com.lt.common_app.base

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlin.reflect.KClass

/**
 * creator: lt  2021/5/23  lt.dygzs@qq.com
 * effect :
 * warning:
 */
actual abstract class BaseComposeActivity actual constructor() {
    actual val mainScope: CoroutineScope= MainScope()

    actual open fun getTitleText(): String {
        return this::class.simpleName!!.replace("Activity", "")
    }

    actual open fun mOnCreate() {
    }

    @Composable
    actual abstract fun ComposeContent()

    actual fun mFinish() {
    }

    actual fun String.showToast() {
    }

    actual fun jump(clazz: KClass<out BaseComposeActivity>) {
    }

}