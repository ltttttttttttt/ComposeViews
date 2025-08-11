package com.lt.compose_views.util

import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreFoundation.CFAbsoluteTimeGetCurrent

@OptIn(ExperimentalForeignApi::class)
actual fun _currentTimeMillis(): Long {
    return (CFAbsoluteTimeGetCurrent() * 1000L).toLong()
}