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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

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
fun <T> rememberMutableStateOf(value: T): MutableState<T> = remember { mutableStateOf(value) }

@Composable
fun <T> rememberMutableStateOf(key1: Any?, value: T): MutableState<T> =
    remember(key1 = key1) { mutableStateOf(value) }

@Composable
fun <T> rememberMutableStateOf(key1: Any?, key2: Any?, value: T): MutableState<T> =
    remember(key1 = key1, key2 = key2) { mutableStateOf(value) }

@Composable
fun <T> rememberMutableStateOf(key1: Any?, key2: Any?, key3: Any?, value: T): MutableState<T> =
    remember(key1 = key1, key2 = key2, key3 = key3) { mutableStateOf(value) }