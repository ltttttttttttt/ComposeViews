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

package com.lt.compose_views.compose_pager

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.Flow

/**
 * ComposePager的状态
 * State of the [ComposePager]
 */
@Stable
class ComposePagerState {

    //当前ComposePager所在的索引
    internal val currSelectIndex: MutableState<Int> = mutableStateOf(0)

    //拖动的动画实现
    internal val offsetAnim = Animatable(0f)

    //翻页标志位
    internal var pageChangeAnimFlag by mutableStateOf<PageChangeAnimFlag?>(null)

    //记录ComposePager的宽高中的对应方向的值
    internal var mainAxisSize by mutableStateOf(0)

    //组件的方向信息
    internal var orientation: Orientation = Orientation.Horizontal

    /**
     * 获取ComposePager当前所在的索引
     * Get current index in the [ComposePager]
     */
    fun getCurrSelectIndex(): Int = currSelectIndex.value

    /**
     * 获取ComposePager所在的索引的state对象
     * Get the [State] of current index of the [ComposePager]
     */
    fun getCurrSelectIndexState(): State<Int> = currSelectIndex

    /**
     * 创建ComposePager当前所在的索引的flow对象
     * Create the [Flow] of current index in the [ComposePager]
     */
    fun createCurrSelectIndexFlow(): Flow<Int> = snapshotFlow { currSelectIndex.value }

    /**
     * 动画是否执行中
     * Whether the [offsetAnim] is running
     */
    fun isAnimRunning(): Boolean = offsetAnim.isRunning

    /**
     * 获取Offset偏移量的state对象
     * Get the [State] of the offset
     */
    fun getOffsetState(): State<Float> = offsetAnim.asState()

    /**
     * 创建子项Offset偏移比例的flow对象
     * Create the [Flow] of percent of offset
     */
    fun createChildOffsetPercentFlow(): Flow<Float> = snapshotFlow {
        val mainAxisSize = mainAxisSize
        if (mainAxisSize == 0)
            0f
        else {
            val percent = offsetAnim.value / mainAxisSize
            0 - (percent + getCurrSelectIndex())
        }
    }

    /**
     * 切换选中的页数,无动画
     * Set the current index, no animate
     */
    fun setPageIndex(index: Int) {
        pageChangeAnimFlag = PageChangeAnimFlag.GoToPageNotAnim(index)
    }

    /**
     * 切换选中的页数,有动画
     * Set the current index, with animate
     */
    fun setPageIndexWithAnimate(index: Int) {
        val currIndex = currSelectIndex.value
        if (index == currIndex - 1) {
            pageChangeAnimFlag = PageChangeAnimFlag.Prev
        } else if (index == currIndex + 1) {
            pageChangeAnimFlag = PageChangeAnimFlag.Next
        } else {
            pageChangeAnimFlag = PageChangeAnimFlag.GoToPageWithAnim(index)
        }
    }

    /**
     * 设置偏移量
     * Set the offset
     */
    suspend fun setOffset(offset: Float) {
        offsetAnim.snapTo(offset - mainAxisSize * getCurrSelectIndex())
    }

    suspend fun setOffsetWithAnimate(offset: Float) {
        offsetAnim.animateTo(offset - mainAxisSize * getCurrSelectIndex())
    }

    /**
     * 设置偏移量百分比
     * Set the percent of offset
     */
    suspend fun setOffsetPercent(percent: Float) {
        setOffset(percent * mainAxisSize)
    }
}

/**
 * 创建一个[remember]的[ComposePagerState]
 * Create the [ComposePagerState] of [remember]
 */
@Composable
fun rememberComposePagerState() = remember { ComposePagerState() }
