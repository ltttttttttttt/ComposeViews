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

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.compose_views.other.HorizontalSpace
import com.lt.compose_views.refresh_layout.RefreshLayoutState
import com.lt.compose_views.res.Strings
import com.lt.compose_views.util.Color333
import io.github.ltttttttttttt.composeviews.generated.resources.Res
import io.github.ltttttttttttt.composeviews.generated.resources.compose_views_refresh_layout_loading
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

/**
 * creator: lt  2022/9/18  lt.dygzs@qq.com
 * effect : 上拉加载的刷新组件
 *          Pull-up loaded refresh component
 * warning:
 * @param isLoadFinish 是否加载完毕(后面加载不出数据了)
 *                     Is it loaded
 */
@OptIn(ExperimentalResourceApi::class)
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
                painter = painterResource(Res.drawable.compose_views_refresh_layout_loading),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .rotate(rotate)
            )
            HorizontalSpace(dp = 10)
        }
        Text(
            text = if (isLoadFinish)
                Strings.getNoMoreDataString()
            else
                Strings.getLoadingString(),
            fontSize = 14.sp,
            color = Color333,
        )
    }
}