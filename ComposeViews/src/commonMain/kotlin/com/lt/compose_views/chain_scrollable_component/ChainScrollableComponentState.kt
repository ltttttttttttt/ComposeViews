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

package com.lt.compose_views.chain_scrollable_component

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.Stable
import com.lt.compose_views.util.ComposePosition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * creator: lt  2022/9/29  lt.dygzs@qq.com
 * effect : [ChainScrollableComponent]的状态
 *          State of the [ChainScrollableComponent]
 * warning:
 */
@Stable
class ChainScrollableComponentState internal constructor(
    val minPx: Float,
    val maxPx: Float,
    val composePosition: ComposePosition,
    val coroutineScope: CoroutineScope,
    private val onScrollStop: ((state: ChainScrollableComponentState) -> Unit)?,
) {
    val orientationIsHorizontal = composePosition.isHorizontal()

    //滚动的位置的动画对象
    internal val scrollPosition =
        Animatable(if (composePosition == ComposePosition.Bottom || composePosition == ComposePosition.End) maxPx else 0f)

    /**
     * 获取滚动的位置的值
     * Get number of scroll position
     */
    fun getScrollPositionValue(): Float = scrollPosition.value

    /**
     * 获取滚动的位置的百分比,0f-1f,min-max
     * Get percentage of scroll position
     */
    fun getScrollPositionPercentage(): Float = abs(getScrollPositionValue() / (maxPx - minPx))

    /**
     * 修改滚动的位置
     * Set number of scroll position
     */
    fun setScrollPosition(value: Float) {
        coroutineScope.launch {
            scrollPosition.snapTo(value)
        }
    }

    suspend fun snapToScrollPosition(value: Float) {
        scrollPosition.snapTo(value)
    }

    /**
     * 以动画形式修改滚动的位置
     * Set number of scroll position, with animate
     */
    fun setScrollPositionWithAnimate(value: Float) {
        coroutineScope.launch {
            scrollPosition.animateTo(value)
        }
    }

    suspend fun animateToScrollPosition(value: Float) {
        scrollPosition.animateTo(value)
    }

    //调用[onScrollStop],触发停止滚动时的回调
    internal fun callOnScrollStop() {
        onScrollStop?.invoke(this)
    }
}