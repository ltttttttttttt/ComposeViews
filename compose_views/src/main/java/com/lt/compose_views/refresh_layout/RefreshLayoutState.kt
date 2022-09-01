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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.lt.compose_views.util.ComposePosition

/**
 * creator: lt  2022/9/1  lt.dygzs@qq.com
 * effect : RefreshLayout的状态
 * warning:
 */
class RefreshLayoutState {
    /**
     * 刷新布局内容区域的组件状态
     */
    val refreshContentState = mutableStateOf(RefreshContentStateEnum.Stop)

    /**
     * 刷新布局内容区域的Offset(位移)的状态
     */
    val refreshContentOffsetState = mutableStateOf(0f)

    /**
     * 子内容区域的Offset(位移)的状态,如果childIsMove==false,则一直为0
     */
    val contentOffsetState = mutableStateOf(0f)

    /**
     * composePosition的状态,参考[RefreshLayout]的参数
     */
    val composePositionState = mutableStateOf(ComposePosition.Top)
}

/**
 * 创建一个[remember]的[RefreshLayoutState]
 */
@Composable
fun rememberRefreshLayoutState() = remember { RefreshLayoutState() }