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

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.LocalIndexToKey
import com.lt.compose_views.util.DragInteractionSource
import kotlinx.coroutines.delay

/**
 * creator: lt  2022/6/25  lt.dygzs@qq.com
 * effect : 可以自动循环轮播的ComposePager
 *          [ComposePager] that can auto scroll
 * warning:
 * @param pageCount 一共有多少页
 *                  Sum page count
 * @param modifier 修饰
 * @param bannerState Banner的状态
 *                    Banner's state
 * @param orientation 滑动的方向
 *                    Scroll orientation
 * @param userEnable 用户是否可以滑动,等于false时用户滑动无反应,但代码可以执行翻页
 *                   Whether the user can scroll
 * @param autoScroll 是否自动滚动
 *                   Whether to scroll automatically
 * @param autoScrollTime 自动滚动间隔时间
 *                       Auto scroll interval
 * @param content compose内容区域
 *                Content of compose
 */
@Composable
fun Banner(
    pageCount: Int,
    modifier: Modifier = Modifier,
    bannerState: BannerState = rememberBannerState(),
    orientation: Orientation = Orientation.Horizontal,
    userEnable: Boolean = true,
    autoScroll: Boolean = true,
    autoScrollTime: Long = 3000,
    content: @Composable BannerScope.() -> Unit
) {
    if (pageCount <= 0)
        return
    //是否正在滚动倒计时中
    var scrolling by remember(key1 = autoScroll, key2 = pageCount) {
        mutableStateOf(autoScroll && pageCount > 1)
    }
    val scrollableInteractionSource = remember(key1 = autoScroll, key2 = pageCount) {
        if (!autoScroll)
            return@remember null
        if (pageCount <= 1) {
            scrolling = false
            return@remember null
        }
        DragInteractionSource { interaction ->
            scrolling = interaction !is DragInteraction.Start
        }
    }
    //计算总共多少页
    val maxPageCount = remember(pageCount) {
        bannerState.pageCount = pageCount
        bannerState.composePagerState.setPageIndex(pageCount * bannerState.startMultiple)
        minOf(pageCount * bannerState.sumMultiple, Int.MAX_VALUE)
    }

    //自动滚动
    if (scrolling)
        LaunchedEffect(key1 = autoScrollTime, block = {
            while (true) {
                delay(autoScrollTime)
                val index = bannerState.composePagerState.currSelectIndex.value
                if (index + 1 >= maxPageCount)
                    bannerState.composePagerState.setPageIndex(pageCount * bannerState.startMultiple)
                else
                    bannerState.composePagerState.setPageIndexWithAnimate(index + 1)
            }
        })

    CompositionLocalProvider(LocalIndexToKey provides { it % pageCount }) {
        //使用ComposePager放置元素
        ComposePager(
            pageCount = maxPageCount,
            modifier = modifier,
            composePagerState = bannerState.composePagerState,
            orientation = orientation,
            userEnable = userEnable,
            pageCache = maxOf(1, (pageCount - 1) / 2),
            scrollableInteractionSource = scrollableInteractionSource,
        ) {
            content(BannerScope(index))
        }
    }
}