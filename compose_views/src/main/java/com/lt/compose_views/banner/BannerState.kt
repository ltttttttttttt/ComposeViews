package com.lt.compose_views.banner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import com.lt.compose_views.compose_pager.ComposePagerState
import com.lt.compose_views.compose_pager.PageChangeAnimFlag
import kotlinx.coroutines.flow.Flow

/**
 * Banner的compose作用域
 */
class BannerState {
    //起始倍数,用于支持用户开始就向左划
    internal val startMultiple = 50

    //总倍数,正常情况下Banner可以循环滑动的次数
    internal val sumMultiple = 5000

    //内部的ComposePager的state
    internal val composePagerState: ComposePagerState = ComposePagerState()

    internal var pageCount: Int = 1

    /**
     * 获取ComposePager当前所在的索引
     */
    fun getCurrSelectIndex(): Int = composePagerState.getCurrSelectIndex() % pageCount

    /**
     * 获取ComposePager所在的索引的state对象
     */
    fun getCurrSelectIndexState(): Flow<Int> = snapshotFlow {
        composePagerState.getCurrSelectIndexState().value % pageCount
    }

    /**
     * 动画是否执行中
     */
    fun isAnimRunning(): Boolean = composePagerState.isAnimRunning()

    /**
     * 获取Offset偏移量的state对象
     */
    fun getOffsetState(): State<Float> = composePagerState.getOffsetState()

    /**
     * 切换选中的页数,无动画
     */
    fun setPageIndex(index: Int) {
        composePagerState.setPageIndex(pageCount * startMultiple + index)
    }

    /**
     * 切换选中的页数,有动画
     */
    fun setPageIndexWithAnim(index: Int) {
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
 */
@Composable
fun rememberBannerState() = remember { BannerState() }
