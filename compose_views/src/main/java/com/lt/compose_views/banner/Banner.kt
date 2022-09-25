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

package com.lt.compose_views.banner

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.LocalIndexToKey
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * creator: lt  2022/6/25  lt.dygzs@qq.com
 * effect : 可以自动循环轮播的ComposePager
 * warning:
 * @param pageCount 一共有多少页
 * @param modifier 修饰
 * @param bannerState Banner的状态
 * @param orientation 滑动的方向
 * @param userEnable 用户是否可以滑动,等于false时用户滑动无反应,但代码可以执行翻页
 * @param autoScroll 是否自动滚动
 * @param autoScrollTime 自动滚动间隔时间
 * @param content compose内容区域
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
    var scrolling by remember(autoScroll) {
        mutableStateOf(autoScroll)
    }
    val scrollableInteractionSource = remember(autoScroll) {
        if (!autoScroll)
            return@remember null
        object : MutableInteractionSource {
            override val interactions: Flow<Interaction>
                get() = flowOf()

            override suspend fun emit(interaction: Interaction) {
                scrolling = interaction !is DragInteraction.Start
            }

            override fun tryEmit(interaction: Interaction): Boolean {
                scrolling = interaction !is DragInteraction.Start
                return true
            }

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
                    bannerState.composePagerState.setPageIndexWithAnim(index + 1)
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
            scrollableInteractionSource = scrollableInteractionSource,
        ) {
            content(BannerScope(index))
        }
    }
}