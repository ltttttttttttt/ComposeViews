/*
 * Copyright lt 2022
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