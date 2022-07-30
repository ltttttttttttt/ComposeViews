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

package com.lt.compose_views.compose_pager

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.CoroutineScope

/**
 * ComposePager的compose作用域
 */
class ComposePagerState {

    //当前ComposePager所在的索引
    internal val currSelectIndex: MutableState<Int> = mutableStateOf(0)

    //拖动的动画实现
    internal val offsetAnim = Animatable(0f)

    //翻页标志位
    internal var pageChangeAnimFlag by mutableStateOf<PageChangeAnimFlag?>(null)

    //记录ComposePager的宽高中的对应方向的值
    internal var mainAxisSize by mutableStateOf(0)

    //用于配合滑动和动画
    internal var mOffset by mutableStateOf<Float?>(null)

    //是否增加n个页数的偏移量,用于处理某些机型的闪动问题
    internal var plusPageOffset by mutableStateOf(0)

    /**
     * 监听用户开始滑动
     */
    var onUserDragStarted: (suspend CoroutineScope.(startedPosition: Offset) -> Unit)? = null

    /**
     * 监听用户结束滑动
     */
    var onUserDragStopped: (suspend CoroutineScope.(velocity: Float) -> Unit)? = null

    /**
     * 获取ComposePager当前所在的索引
     */
    fun getCurrSelectIndex(): Int = currSelectIndex.value

    /**
     * 获取ComposePager所在的索引的state对象
     */
    fun getCurrSelectIndexState(): State<Int> = currSelectIndex

    /**
     * 动画是否执行中
     */
    fun isAnimRunning(): Boolean = offsetAnim.isRunning

    /**
     * 获取Offset偏移量的state对象
     */
    fun getOffsetState(): State<Float> = offsetAnim.asState()

    /**
     * 切换选中的页数,无动画
     */
    fun setPageIndex(index: Int) {
        pageChangeAnimFlag = PageChangeAnimFlag.GoToPageNotAnim(index)
    }

    /**
     * 切换选中的页数,有动画
     */
    fun setPageIndexWithAnim(index: Int) {
        val currIndex = currSelectIndex.value
        if (index == currIndex - 1) {
            pageChangeAnimFlag = PageChangeAnimFlag.Prev
        } else if (index == currIndex + 1) {
            pageChangeAnimFlag = PageChangeAnimFlag.Next
        } else {
            setPageIndex(index)
        }
    }
}

/**
 * 创建一个[remember]的[ComposePagerState]
 */
@Composable
fun rememberComposePagerState() = remember { ComposePagerState() }
