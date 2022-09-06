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

package com.lt.compose_views.image_banner

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import com.lt.compose_views.banner.Banner
import com.lt.compose_views.banner.BannerState
import com.lt.compose_views.banner.rememberBannerState
import com.lt.compose_views.pager_indicator.PagerIndicator

/**
 * creator: lt  2022/6/27  lt.dygzs@qq.com
 * effect : 展示图片的Banner
 * warning: 需要引入coil库,如: io.coil-kt:coil-compose:1.4.0
 * [images]图片地址列表
 * [indicatorItem]未被选中的指示器,如果为null则不展示指示器
 * [selectIndicatorItem]被选中的指示器,如果为null则不展示指示器
 * [modifier]修饰
 * [bannerState]Banner的状态
 * [orientation]滑动的方向
 * [autoScrollTime]自动滚动间隔时间
 * [onItemClick]条目的点击事件
 */
@Composable
fun ImageBanner(
    images: List<String>,
    indicatorItem: @Composable ((index: Int) -> Unit)?,
    selectIndicatorItem: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
    bannerState: BannerState = rememberBannerState(),
    orientation: Orientation = Orientation.Horizontal,
    autoScrollTime: Long = 3000,
    onItemClick: (index: Int) -> Unit
) {
    Box(modifier = modifier) {
        Banner(
            pageCount = images.size,
            bannerState = bannerState,
            orientation = orientation,
            autoScrollTime = autoScrollTime,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = rememberImagePainter(data = images[index]),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onItemClick(index) },
                contentScale = ContentScale.Crop
            )
        }
        if (indicatorItem != null && selectIndicatorItem != null) {
            val offsetPercent by remember(orientation) { bannerState.createChildOffsetPercentFlow() }
                .collectAsState(initial = 0f)
            PagerIndicator(
                size = images.size,
                offsetPercentWithSelect = offsetPercent,
                selectIndex = bannerState.getCurrSelectIndex(),
                indicatorItem = indicatorItem,
                selectIndicatorItem = selectIndicatorItem,
                orientation = orientation,
            )
        }
    }
}