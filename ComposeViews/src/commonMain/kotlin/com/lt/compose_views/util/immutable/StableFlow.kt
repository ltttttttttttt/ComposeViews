package com.lt.compose_views.util.immutable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.Flow

/**
 * creator: lt  2024/1/3  lt.dygzs@qq.com
 * effect : 稳定的[Flow],在Compose中也是稳定的
 *          Stable [Flow], also stable in Compose
 * warning:
 */
@Stable
class StableFlow<T>(private val flow: Flow<T>) : Flow<T> by flow

/**
 * [remember]一个[StableFlow],在Composable函数中推荐使用此函数
 * [remember] An [StableFlow] is recommended for use in the Composable function
 */
@Composable
fun <T> rememberStableFlow(flow: Flow<T>): StableFlow<T> =
    remember(flow) { StableFlow(flow) }