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
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

/**
 * creator: lt  2022/6/27  lt.dygzs@qq.com
 * effect : 工具类或扩展方法
 * warning:
 */

/**
 * remember一个lambda
 */
@Composable
inline fun <T : Function<*>?> rel(lambda: T): T = remember { lambda }

/**
 * 获取居中的值
 */
internal fun midOf(min: Int, number: Int, max: Int): Int = maxOf(min, minOf(number, max))
internal fun midOf(min: Float, number: Float, max: Float): Float = maxOf(min, minOf(number, max))

/**
 * 根据this的百分比(0-1或1-0)来计算start到end对应的值,并返回
 */
internal fun Float/*percentage*/.getPercentageValue(startValue: Float, endValue: Float): Float =
    if (startValue == endValue) startValue
    else (endValue - startValue) * this + startValue

internal fun Float/*percentage*/.getPercentageValue(startValue: Color, endValue: Color): Color =
    Color(
        alpha = getPercentageValue(startValue.alpha, endValue.alpha),
        red = getPercentageValue(startValue.red, endValue.red),
        green = getPercentageValue(startValue.green, endValue.green),
        blue = getPercentageValue(startValue.blue, endValue.blue),
    )