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

package com.lt.compose_views.refresh_layout

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.*
import com.lt.compose_views.util.ComposePosition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * creator: lt  2022/9/1  lt.dygzs@qq.com
 * effect : RefreshLayout的状态
 *          State of the [RefreshLayout]
 * warning:
 * @param onRefreshListener 触发刷新时的回调(不保证线程)
 *                          Callback when refresh is triggered
 */
@Stable
class RefreshLayoutState(
    internal val onRefreshListener: RefreshLayoutState.() -> Unit
) {
    //刷新布局内容区域的组件状态
    internal val refreshContentState = mutableStateOf(RefreshContentStateEnum.Stop)

    //刷新布局内容区域的Offset(位移)的状态和子内容区域的Offset(位移)的状态,如果contentIsMove==false,则一直为0,单位px
    internal val refreshContentOffsetState = Animatable(0f)

    //composePosition的状态,参考[RefreshLayout]的参数
    internal val composePositionState = mutableStateOf(ComposePosition.Top)

    //刷新布局拖动的阈值,单位px
    internal val refreshContentThresholdState = mutableStateOf(0f)

    //协程作用域
    internal lateinit var coroutineScope: CoroutineScope

    /**
     * 获取刷新布局内容区域的组件状态
     * Get the [State] of the refresh content
     */
    fun getRefreshContentState(): State<RefreshContentStateEnum> = refreshContentState

    /**
     * 创建刷新布局内容区域的Offset(位移)的flow
     * Create the [Flow] of the offset
     */
    fun createRefreshContentOffsetFlow(): Flow<Float> =
        snapshotFlow { refreshContentOffsetState.value }

    /**
     * 获取composePosition的状态,参考[RefreshLayout]的参数
     * Get the [State] of the [ComposePosition]
     */
    fun getComposePositionState(): State<ComposePosition> = composePositionState

    /**
     * 获取刷新布局拖动的阈值,单位px
     * Get threshold of the refresh content
     */
    fun getRefreshContentThreshold(): Float = refreshContentThresholdState.value

    /**
     * 刷新布局内容区域的Offset的值,单位px
     * Get the offset of content area
     */
    fun getRefreshContentOffset(): Float = refreshContentOffsetState.value

    /**
     * 设置刷新布局的状态
     * Set the state of refresh content
     */
    fun setRefreshState(state: RefreshContentStateEnum) {
        when (state) {
            RefreshContentStateEnum.Stop -> {
                if (refreshContentState.value == RefreshContentStateEnum.Stop)
                    return
                if (!this::coroutineScope.isInitialized)
                    throw IllegalStateException("[RefreshLayoutState]还未初始化完成,请在[LaunchedEffect]中或composable至少组合一次后使用此方法")
                coroutineScope.launch {
                    refreshContentState.value = RefreshContentStateEnum.Stop
                    delay(300)
                    refreshContentOffsetState.animateTo(0f)
                }
            }
            RefreshContentStateEnum.Refreshing -> {
                if (refreshContentState.value == RefreshContentStateEnum.Refreshing)
                    return
                if (!this::coroutineScope.isInitialized)
                    throw IllegalStateException("[RefreshLayoutState]还未初始化完成,请在[LaunchedEffect]中或composable至少组合一次后使用此方法")
                coroutineScope.launch {
                    refreshContentState.value = RefreshContentStateEnum.Refreshing
                    onRefreshListener()
                    animateToThreshold()
                }
            }
            RefreshContentStateEnum.Dragging -> throw IllegalStateException("设置为[RefreshContentStateEnum.Dragging]无意义")
        }
    }

    //偏移量归位,并检查是否超过了刷新阈值,如果超过了执行刷新逻辑
    internal fun offsetHoming() {
        coroutineScope.launch {
            //检查是否进入了刷新状态
            if (abs(refreshContentOffsetState.value) >= refreshContentThresholdState.value) {
                refreshContentState.value = RefreshContentStateEnum.Refreshing
                onRefreshListener()
                animateToThreshold()
            } else {
                refreshContentOffsetState.animateTo(0f)
                refreshContentState.value = RefreshContentStateEnum.Stop
            }
        }
    }

    //动画滑动至阈值处
    private suspend fun animateToThreshold() {
        val composePosition = composePositionState.value
        if (composePosition == ComposePosition.Start || composePosition == ComposePosition.Top)
            refreshContentOffsetState.animateTo(refreshContentThresholdState.value)
        else
            refreshContentOffsetState.animateTo(-refreshContentThresholdState.value)
    }

    //增加偏移量
    internal fun offset(refreshContentOffset: Float) {
        coroutineScope.launch {
            val targetValue = refreshContentOffsetState.value + refreshContentOffset
            if (refreshContentState.value != RefreshContentStateEnum.Dragging && targetValue != 0f) {
                refreshContentState.value = RefreshContentStateEnum.Dragging
            }
            refreshContentOffsetState.snapTo(targetValue)
        }
    }
}

/**
 * 创建一个[remember]的[RefreshLayoutState]
 * Create the [RefreshLayoutState] of [remember]
 * @param onRefreshListener 触发刷新时的回调
 *                          Callback when refresh is triggered
 */
@Composable
fun rememberRefreshLayoutState(onRefreshListener: RefreshLayoutState.() -> Unit) =
    remember { RefreshLayoutState(onRefreshListener) }