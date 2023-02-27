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

package com.lt.compose_views.image_banner

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lt.compose_views.banner.Banner
import com.lt.compose_views.banner.BannerScope
import com.lt.compose_views.banner.BannerState
import com.lt.compose_views.banner.rememberBannerState
import com.lt.compose_views.pager_indicator.PagerIndicator
import com.lt.compose_views.pager_indicator.PagerIndicatorScope

/**
 * creator: lt  2022/6/27  lt.dygzs@qq.com
 * effect : 展示图片的Banner
 *          [Banner] showing images
 * warning:
 * @param imageSize 图片数量
 *                  Number of images
 * @param imageContent 放置图片的content
 *                     Content of the images
 * @param indicatorItem 未被选中的指示器,如果为null则不展示指示器
 *                      The indicator, if null, do not display indicator
 * @param selectIndicatorItem 被选中的指示器,如果为null则不展示指示器
 *                            The indicator, if null, do not display indicator
 * @param modifier 修饰
 * @param bannerState Banner的状态
 *                    Banner's state
 * @param orientation 滑动的方向
 *                    Orientation of indicators
 * @param autoScroll 是否自动滚动
 *                   Whether to scroll automatically
 * @param autoScrollTime 自动滚动间隔时间
 *                       Auto scroll interval
 */
@Composable
fun ImageBanner(
    imageSize: Int,
    imageContent: @Composable BannerScope.() -> Unit,
    indicatorItem: @Composable ((index: Int) -> Unit)?,
    selectIndicatorItem: @Composable (PagerIndicatorScope.() -> Unit)?,
    modifier: Modifier = Modifier,
    bannerState: BannerState = rememberBannerState(),
    orientation: Orientation = Orientation.Horizontal,
    autoScroll: Boolean = true,
    autoScrollTime: Long = 3000,
) {
    Box(modifier = modifier) {
        //banner部分
        Banner(
            pageCount = imageSize,
            bannerState = bannerState,
            orientation = orientation,
            autoScroll = autoScroll,
            autoScrollTime = autoScrollTime,
            modifier = Modifier.fillMaxSize()
        ) {
            imageContent()
        }
        //指示器部分
        if (indicatorItem != null && selectIndicatorItem != null) {
            PagerIndicator(
                size = imageSize,
                offsetPercentWithSelectFlow = remember(orientation) { bannerState.createChildOffsetPercentFlow() },
                selectIndexFlow = remember(orientation) { bannerState.createCurrSelectIndexFlow() },
                indicatorItem = indicatorItem,
                selectIndicatorItem = selectIndicatorItem,
                orientation = orientation,
                modifier = Modifier
                    .padding(8.dp)
                    .align(if (orientation == Orientation.Horizontal) Alignment.BottomCenter else Alignment.CenterEnd)
            )
        }
    }
}