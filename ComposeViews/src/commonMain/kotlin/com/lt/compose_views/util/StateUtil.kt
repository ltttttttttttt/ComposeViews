/*
 * Copyright lt 2023
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lt.compose_views.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * creator: lt  2021/4/14  lt.dygzs@qq.com
 * effect : 状态工具类
 * warning:
 */

/**
 * 快捷使用remember { mutableStateOf(T) }
 * Quick use remember { mutableStateOf(T) }
 */
@Composable
inline fun <T> rememberMutableStateOf(
    crossinline initValue: @DisallowComposableCalls () -> T
) = remember { mutableStateOf(initValue()) }

@Composable
inline fun <T> rememberMutableStateOf(
    key1: Any?,
    crossinline initValue: @DisallowComposableCalls () -> T
): MutableState<T> = remember(key1 = key1) { mutableStateOf(initValue()) }

@Composable
inline fun <T> rememberMutableStateOf(
    key1: Any?,
    key2: Any?,
    crossinline initValue: @DisallowComposableCalls () -> T
): MutableState<T> = remember(key1 = key1, key2 = key2) { mutableStateOf(initValue()) }

@Composable
inline fun <T> rememberMutableStateOf(
    key1: Any?,
    key2: Any?,
    key3: Any?,
    crossinline initValue: @DisallowComposableCalls () -> T
): MutableState<T> = remember(key1 = key1, key2 = key2, key3 = key3) { mutableStateOf(initValue()) }

@Composable
inline fun rememberMutableIntStateOf(
    crossinline initValue: @DisallowComposableCalls () -> Int
): MutableIntState = remember { mutableIntStateOf(initValue()) }

@Composable
inline fun rememberMutableFloatStateOf(
    crossinline initValue: @DisallowComposableCalls () -> Float
): MutableFloatState = remember { mutableFloatStateOf(initValue()) }

@Composable
inline fun rememberMutableLongStateOf(
    crossinline initValue: @DisallowComposableCalls () -> Long
): MutableLongState = remember { mutableLongStateOf(initValue()) }

@Composable
inline fun rememberMutableDoubleStateOf(
    crossinline initValue: @DisallowComposableCalls () -> Double
): MutableDoubleState = remember { mutableDoubleStateOf(initValue()) }

@Composable
fun <T> rememberMutableStateListOf(): SnapshotStateList<T> = remember { SnapshotStateList() }

@Composable
inline fun <T> rememberMutableStateListOf(
    crossinline initValue: @DisallowComposableCalls () -> List<T>
): SnapshotStateList<T> = remember {
    SnapshotStateList<T>().apply {
        addAll(initValue())
    }
}

@Deprecated(
    "Need to use a function with the same name as lambda for higher performance", ReplaceWith(
        "rememberMutableStateOf { value }",
        "androidx.compose.runtime.remember",
        "androidx.compose.runtime.mutableStateOf"
    )
)
@Composable
fun <T> rememberMutableStateOf(value: T): MutableState<T> = remember { mutableStateOf(value) }

@Deprecated(
    "Need to use a function with the same name as lambda for higher performance", ReplaceWith(
        "rememberMutableStateOf(key1) { value }",
        "androidx.compose.runtime.remember",
        "androidx.compose.runtime.mutableStateOf"
    )
)
@Composable
fun <T> rememberMutableStateOf(key1: Any?, value: T): MutableState<T> =
    remember(key1 = key1) { mutableStateOf(value) }

@Deprecated(
    "Need to use a function with the same name as lambda for higher performance", ReplaceWith(
        "rememberMutableStateOf(key1, key2) { value }",
        "androidx.compose.runtime.remember",
        "androidx.compose.runtime.mutableStateOf"
    )
)
@Composable
fun <T> rememberMutableStateOf(key1: Any?, key2: Any?, value: T): MutableState<T> =
    remember(key1 = key1, key2 = key2) { mutableStateOf(value) }

@Deprecated(
    "Need to use a function with the same name as lambda for higher performance", ReplaceWith(
        "rememberMutableStateOf(key1, key2, key3) { value }",
        "androidx.compose.runtime.remember",
        "androidx.compose.runtime.mutableStateOf"
    )
)
@Composable
fun <T> rememberMutableStateOf(key1: Any?, key2: Any?, key3: Any?, value: T): MutableState<T> =
    remember(key1 = key1, key2 = key2, key3 = key3) { mutableStateOf(value) }