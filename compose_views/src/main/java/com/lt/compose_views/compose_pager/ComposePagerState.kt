package com.lt.compose_views.compose_pager

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.CoroutineScope

/**
 * ComposePager的compose作用域
 * [currSelectIndex]当前ComposePager所在的索引
 * [onUserDragStarted]监听用户开始滑动
 * [onUserDragStopped]监听用户结束滑动
 */
class ComposePagerState(
    val currSelectIndex: MutableState<Int>,
    var onUserDragStarted: (suspend CoroutineScope.(startedPosition: Offset) -> Unit)? = null,
    var onUserDragStopped: (suspend CoroutineScope.(velocity: Float) -> Unit)? = null,
) {
    //拖动的动画实现
    internal val offsetAnim = Animatable(0f)

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
        currSelectIndex.value = index
    }

    /**
     * 切换选中的页数,有动画
     */
    fun setPageIndexWithAnim(index: Int) {
        // TODO by lt 2022/6/27 18:19 增加使用动画切换页数的功能

    }
}

/**
 * 创建一个[remember]的[ComposePagerState]
 */
@Composable
fun rememberComposePagerState() = remember { ComposePagerState(mutableStateOf(0)) }
