package com.lt.compose_views

/**
 * creator: lt  2022/6/27  lt.dygzs@qq.com
 * effect : 工具类或扩展方法
 * warning:
 */

/**
 * 获取居中的值
 */
internal fun midOf(min: Int, number: Int, max: Int): Int = maxOf(min, minOf(number, max))
internal fun midOf(min: Float, number: Float, max: Float): Float = maxOf(min, minOf(number, max))