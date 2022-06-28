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
    // TODO by lt 2022/6/27 18:19 增加使用动画切换页数的功能,和普通切换页数的功能
    /**
     * 动画是否执行中
     */
    fun isAnimRunning(): Boolean = anim?.isRunning ?: false

    /**
     * 获取Offset偏移量的state对象
     */
    fun getOffsetState(): State<Float>? = anim?.asState()

    internal var anim: Animatable<Float, *>? = null
}

/**
 * 创建一个[remember]的[ComposePagerState]
 */
@Composable
fun rememberComposePagerState() = remember { ComposePagerState(mutableStateOf(0)) }
