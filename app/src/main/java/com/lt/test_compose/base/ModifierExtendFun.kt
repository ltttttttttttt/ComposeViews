package com.lt.test_compose.base

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * creator: lt  2021/4/13  lt.dygzs@qq.com
 * effect : 修饰符扩展函数
 * warning:
 */

typealias M = Modifier

/**
 * 宽和高
 */
fun w(width: Int): Modifier = Modifier.width(width.dp)
fun h(height: Int): Modifier = Modifier.height(height.dp)
fun Modifier.w(width: Int): Modifier = width(width.dp)
fun Modifier.h(height: Int): Modifier = height(height.dp)