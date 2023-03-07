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

package com.lt.compose_views.pager_indicator

import kotlin.jvm.JvmInline

/**
 * creator: lt  2022/10/24  lt.dygzs@qq.com
 * effect : 指示器的坐标数据
 *          Location info of the indicators
 * warning: 横向为[start,center,end],竖向为[top,center,bottom]
 */
@JvmInline
value class IndicatorsInfo(val data: IntArray) {
    /**
     * 获取相应[index]位置指示器的start坐标
     * Get start location of the indicators with index
     */
    fun getIndicatorStart(index: Int): Int = data[index * 3]
    inline fun getIndicatorStartOrElse(index: Int, defaultValue: (Int) -> Int): Int =
        data.getOrElse(index * 3, defaultValue)

    /**
     * 获取相应[index]位置指示器的center坐标
     * Get center location of the indicators with index
     */
    fun getIndicatorCenter(index: Int): Int = data[index * 3 + 1]
    inline fun getIndicatorCenterOrElse(index: Int, defaultValue: (Int) -> Int): Int =
        data.getOrElse(index * 3 + 1, defaultValue)

    /**
     * 获取相应[index]位置指示器的end坐标
     * Get end location of the indicators with index
     */
    fun getIndicatorEnd(index: Int): Int = data[index * 3 + 2]
    inline fun getIndicatorEndOrElse(index: Int, defaultValue: (Int) -> Int): Int =
        data.getOrElse(index * 3 + 2, defaultValue)

    /**
     * 获取相应[index]位置指示器的宽或高(根据方向)
     * Get size of the indicators with index
     */
    fun getIndicatorSize(index: Int): Int = getIndicatorEnd(index) - getIndicatorStart(index)

    /**
     * 获取相应[index]位置指示器的坐标数据
     * Set location info of index position indicators
     */
    fun setData(index: Int, start: Int, end: Int) {
        data[index * 3] = start
        data[index * 3 + 1] = (start + end) / 2
        data[index * 3 + 2] = end
    }
}