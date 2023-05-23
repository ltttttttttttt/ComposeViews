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

package com.lt.compose_views.refresh_layout.refresh_content.bottom

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.compose_views.other.HorizontalSpace
import com.lt.compose_views.refresh_layout.RefreshLayoutState
import com.lt.compose_views.res.Res
import com.lt.compose_views.res.getLoadingString
import com.lt.compose_views.res.getNoMoreDataString
import com.lt.compose_views.util.Color333

/**
 * creator: lt  2022/9/18  lt.dygzs@qq.com
 * effect : 上拉加载的刷新组件
 *          Pull-up loaded refresh component
 * warning:
 * @param isLoadFinish 是否加载完毕(后面加载不出数据了)
 *                     Is it loaded
 */
@Composable
fun RefreshLayoutState.LoadMoreRefreshContent(
    isLoadFinish: Boolean = false
) {
    val rotate =
        if (isLoadFinish || getRefreshContentOffset() == 0f) {
            0f
        } else {
            val infiniteTransition = rememberInfiniteTransition()
            infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            ).value
        }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isLoadFinish) {
            Image(
                painter = Res.getRefreshLayoutLoadingPainter(),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .rotate(rotate)
            )
            HorizontalSpace(dp = 10)
        }
        Text(
            text = if (isLoadFinish)
                Res.getNoMoreDataString()
            else
                Res.getLoadingString(),
            fontSize = 14.sp,
            color = Color333,
        )
    }
}