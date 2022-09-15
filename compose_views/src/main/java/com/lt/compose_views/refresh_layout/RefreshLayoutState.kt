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

package com.lt.compose_views.refresh_layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.lt.compose_views.util.ComposePosition

/**
 * creator: lt  2022/9/1  lt.dygzs@qq.com
 * effect : RefreshLayout的状态
 * warning:
 * @param onRefreshListener 触发刷新时的回调
 */
class RefreshLayoutState(
    internal val onRefreshListener: () -> Unit
) {
    //刷新布局内容区域的组件状态
    internal val refreshContentState = mutableStateOf(RefreshContentStateEnum.Stop)

    //刷新布局内容区域的Offset(位移)的状态
    internal val refreshContentOffsetState = mutableStateOf(0f)

    //子内容区域的Offset(位移)的状态,如果childIsMove==false,则一直为0,单位px
    internal val contentOffsetState = mutableStateOf(0f)

    //composePosition的状态,参考[RefreshLayout]的参数
    internal val composePositionState = mutableStateOf(ComposePosition.Top)

    //composePosition的状态,参考[RefreshLayout]的参数,单位px
    internal val refreshContentThresholdState = mutableStateOf(0f)

    //设置偏移量
    internal fun setOffset(refreshContentOffset: Float, contentOffset: Float) {

    }

    //增加偏移量
    internal fun offset(refreshContentOffset: Float, contentOffset: Float) {

    }

    /**
     * 获取刷新布局内容区域的组件状态
     */
    fun getRefreshContentState(): State<RefreshContentStateEnum> = refreshContentState

    /**
     * 获取刷新布局内容区域的Offset(位移)的状态
     */
    fun getRefreshContentOffsetState(): State<Float> = refreshContentOffsetState

    /**
     * 获取子内容区域的Offset(位移)的状态,如果childIsMove==false,则一直为0
     */
    fun getContentOffsetState(): State<Float> = contentOffsetState

    /**
     * 获取composePosition的状态,参考[RefreshLayout]的参数
     */
    fun getComposePositionState(): State<ComposePosition> = composePositionState

    /**
     * 获取composePosition的状态,参考[RefreshLayout]的参数,单位px
     */
    fun getRefreshContentThresholdState(): State<Float> = refreshContentThresholdState

    fun setRefreshState(state: RefreshContentStateEnum) {
        // TODO by lt 2022/9/1 18:12  
    }
}

/**
 * 创建一个[remember]的[RefreshLayoutState]
 * [onRefreshListener]触发刷新时的回调
 */
@Composable
fun rememberRefreshLayoutState(onRefreshListener: () -> Unit) =
    remember { RefreshLayoutState(onRefreshListener) }