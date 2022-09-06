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

package com.lt.compose_views.util

import androidx.compose.animation.core.AnimationConstants
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.monotonicFrameClock
import kotlin.coroutines.coroutineContext

/**
 * creator: lt  2022/7/31  lt.dygzs@qq.com
 * effect : 自定义的动画播放器,逻辑更简单
 * warning: 推荐使用系统的动画api
 * [initialValueWithState]动画要改变的状态,起始动画值为其value值
 * [targetValue]要通过动画转化到的目标值
 * [duration]动画的持续时间
 * [animInterpolator]动画差值器
 */
@OptIn(ExperimentalComposeApi::class)
suspend fun animateWithFloat(
    initialValueWithState: MutableState<Float>,
    targetValue: Float,
    duration: Int = AnimationConstants.DefaultDurationMillis,
    animInterpolator: MAnimInterpolator = DecelerateInterpolator(),
) {
    val startValue = initialValueWithState.value
    val valueToBeTransformed = targetValue - startValue
    val startTime = System.nanoTime()
    val duration = duration * 1000000L
    val frameClock = coroutineContext.monotonicFrameClock
    while (System.nanoTime() <= startTime + duration) {
        frameClock.withFrameNanos {
            val progress = animInterpolator.getInterpolation(
                minOf(it - startTime, duration).toFloat() / duration
            )
            val increase = progress * valueToBeTransformed
            initialValueWithState.value = startValue + increase
        }
    }
}

/**
 * 动画差值器
 */
fun interface MAnimInterpolator {
    /**
     * 根据输入的动画进度[input]计算动画进度
     */
    fun getInterpolation(input: Float): Float
}

/**
 * An interpolator where the rate of change starts out quickly and and then decelerates.
 * ps:参考[DecelerateInterpolator],先快后慢
 * [mFactor]Degree to which the animation should be eased. Setting factor to 1.0f produces an upside-down y=x^2 parabola. Increasing factor above 1.0f exaggerates the ease-out effect (i.e., it starts even faster and ends evens slower).
 */
class DecelerateInterpolator(private val mFactor: Float = 1.0f) : MAnimInterpolator {
    override fun getInterpolation(input: Float): Float {
        return if (mFactor == 1.0f) {
            (1.0f - (1.0f - input) * (1.0f - input))
        } else {
            (1.0f - Math.pow((1.0f - input).toDouble(), (2 * mFactor).toDouble())).toFloat()
        }
    }
}