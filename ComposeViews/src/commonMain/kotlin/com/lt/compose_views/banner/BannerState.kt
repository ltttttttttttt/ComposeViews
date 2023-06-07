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

package com.lt.compose_views.banner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.ComposePagerState
import com.lt.compose_views.compose_pager.PageChangeAnimFlag
import kotlinx.coroutines.flow.Flow

/**
 * Banner的状态
 * State of the [Banner]
 */
@Stable
class BannerState {
    //起始倍数,用于支持用户开始就向左划
    internal val startMultiple = 50

    //总倍数,正常情况下Banner可以循环滑动的次数
    internal val sumMultiple = 5000

    //内部的ComposePager的state
    internal val composePagerState: ComposePagerState = ComposePagerState()

    internal var pageCount: Int = 1

    /**
     * 获取Banner当前所在的索引
     * Get current index in the [Banner]
     */
    fun getCurrSelectIndex(): Int = composePagerState.getCurrSelectIndex() % pageCount

    /**
     * 创建Banner当前索引的flow对象
     * Create the [Flow] of the current index of the [Banner]
     */
    fun createCurrSelectIndexFlow(): Flow<Int> = snapshotFlow {
        composePagerState.getCurrSelectIndexState().value % pageCount
    }

    /**
     * 动画是否执行中
     * Whether animate is running
     */
    fun isAnimRunning(): Boolean = composePagerState.isAnimRunning()

    /**
     * 获取Offset偏移量的state对象
     * Get the [State] of the offset
     */
    fun getOffsetState(): State<Float> = composePagerState.getOffsetState()

    /**
     * 创建子项Offset偏移比例的flow对象
     * Create the [Flow] of the percent of the offset
     */
    fun createChildOffsetPercentFlow(): Flow<Float> = snapshotFlow {
        val mainAxisSize = composePagerState.mainAxisSize
        if (mainAxisSize == 0)
            0f
        else {
            val percent = composePagerState.offsetAnim.value / mainAxisSize
            0 - (percent + composePagerState.getCurrSelectIndex())
        }
    }

    /**
     * 切换选中的页数,无动画
     * Set the current index, no animate
     */
    fun setPageIndex(index: Int) {
        composePagerState.setPageIndex(pageCount * startMultiple + index)
    }

    /**
     * 切换选中的页数,有动画
     * Set the current index, with animate
     */
    fun setPageIndexWithAnimate(index: Int) {
        val currIndex = getCurrSelectIndex()
        if (index == currIndex - 1) {
            composePagerState.pageChangeAnimFlag = PageChangeAnimFlag.Prev
        } else if (index == currIndex + 1) {
            composePagerState.pageChangeAnimFlag = PageChangeAnimFlag.Next
        } else {
            setPageIndex(index)
        }
    }
}

/**
 * 创建一个[remember]的[BannerState]
 * Create the [BannerState] of [remember]
 */
@Composable
fun rememberBannerState() = remember { BannerState() }
