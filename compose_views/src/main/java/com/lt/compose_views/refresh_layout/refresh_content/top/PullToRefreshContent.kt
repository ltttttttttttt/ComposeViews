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

package com.lt.compose_views.refresh_layout.refresh_content.top

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.compose_views.R
import com.lt.compose_views.other.HorizontalSpace
import com.lt.compose_views.refresh_layout.RefreshContentStateEnum
import com.lt.compose_views.refresh_layout.RefreshLayoutState
import com.lt.compose_views.util.Color333
import java.lang.Math.abs

/**
 * creator: lt  2022/9/18  lt.dygzs@qq.com
 * effect : 下拉刷新的刷新组件
 *          Refresh component for pull down refresh
 * warning:
 */
@Composable
fun RefreshLayoutState.PullToRefreshContent() {
    val refreshContentState by remember {
        getRefreshContentState()
    }
    Row(
        Modifier
            .fillMaxWidth()
            .height(35.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        when (refreshContentState) {
            RefreshContentStateEnum.Stop -> {
                //no image
            }
            RefreshContentStateEnum.Refreshing -> {
                //循环旋转动画
                val infiniteTransition = rememberInfiniteTransition()
                val rotate by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
                Image(
                    painter = painterResource(id = R.drawable.compose_views_refresh_layout_loading),
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(rotate)
                )
                HorizontalSpace(dp = 10)
            }
            RefreshContentStateEnum.Dragging -> {
                //旋转动画
                val isCannotRefresh =
                    abs(getRefreshContentOffset()) < getRefreshContentThreshold()
                val rotate by animateFloatAsState(targetValue = if (isCannotRefresh) 0f else 180f)
                Image(
                    painter = painterResource(id = R.drawable.compose_views_refresh_layout_arrow),
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(rotate)
                )
                HorizontalSpace(dp = 10)
            }
        }
        Text(
            text = when (refreshContentState) {
                RefreshContentStateEnum.Stop -> stringResource(id = R.string.compose_views_refresh_complete)
                RefreshContentStateEnum.Refreshing -> stringResource(id = R.string.compose_views_refreshing)
                RefreshContentStateEnum.Dragging -> {
                    if (abs(getRefreshContentOffset()) < getRefreshContentThreshold()) {
                        stringResource(id = R.string.compose_views_drop_down_to_refresh)
                    } else {
                        stringResource(id = R.string.compose_views_release_refresh_now)
                    }
                }
            },
            fontSize = 14.sp,
            color = Color333,
        )
    }
}