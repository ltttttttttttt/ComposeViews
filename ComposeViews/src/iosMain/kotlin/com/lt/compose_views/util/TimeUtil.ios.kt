package com.lt.compose_views.util

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

/**
 * 获取当前时间戳(ms)
 */
actual fun _currentTimeMillis(): Long = NSDate().timeIntervalSince1970() * 1000L