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
