package com.lt.compose_views.util.immutable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember

/**
 * creator: lt  2024/1/3  lt.dygzs@qq.com
 * effect : 完全不可变的[List],在Compose中也是稳定的
 *          A completely immutable [List] is also stable in Compose
 * warning:
 */
@Immutable
class ImmutableList<T>(private val list: List<T>) : List<T> by list.toList()

/**
 * [remember]一个[ImmutableList],在Composable函数中推荐使用此函数
 * [remember] An [ImmutableList] is recommended for use in the Composable function
 */
@Composable
fun <T> rememberImmutableList(list: List<T>): ImmutableList<T> =
    remember(list) { ImmutableList(list) }

/**
 * 快速构建[ImmutableList]
 * Quickly build [ImmutableList]
 */
fun <T> immutableListOf(vararg t: T): ImmutableList<T> = ImmutableList(t.toList())

/**
 * 将[List]转换为[ImmutableList]
 * Convert [List] to [ImmutableList]
 */
fun <T> List<T>.toImmutableList(): ImmutableList<T> = ImmutableList(this)