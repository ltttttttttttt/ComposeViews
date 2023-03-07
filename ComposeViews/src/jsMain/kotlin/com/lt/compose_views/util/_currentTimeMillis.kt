package com.lt.compose_views.util

import kotlin.js.Date

/**
 * 获取当前时间戳(ms)
 */
actual fun _currentTimeMillis(): Long = Date.now().toLong()