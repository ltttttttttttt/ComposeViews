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

package com.lt.test_compose

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.ComposePagerState
import com.lt.compose_views.pager_indicator.TextPagerIndicator
import com.lt.test_compose.base.BaseComposeActivity

/**
 * creator: lt  2022/10/22  lt.dygzs@qq.com
 * effect :
 * warning:
 */
class TextPagerIndicatorActivity : BaseComposeActivity() {
    private val colors = mutableStateListOf(
        Color(150, 105, 61, 255) to "暴走",
        Color.DarkGray to "十字斩",
        Color(122, 138, 55, 255) to "崩山击",
        Color(114, 61, 101, 255) to "崩山裂地斩",
        Color.Gray to "怒气爆发",
        Color(112, 62, 11, 255) to "嗜魂封魔斩",
        Color(50, 134, 74, 255) to "噬魂之手",
    )
    private val texts = colors.map { it.second }
    private val pagerState = ComposePagerState()
    private val textColor = Color(0xff999999)
    private val selectTextColor = Color(0xff333333)
    private val selectIndicatorColor = Color(0xff1773FC)

    @Composable
    override fun ComposeContent() {
        val offsetPercent by remember { pagerState.createChildOffsetPercentFlow() }
            .collectAsState(initial = 0f)
        Column {
            TextPagerIndicator(
                texts = texts,
                offsetPercentWithSelect = offsetPercent,
                selectIndex = pagerState.getCurrSelectIndex(),
                fontSize = 16.sp,
                selectFontSize = 20.sp,
                textColor = textColor,
                selectTextColor = selectTextColor,
                selectIndicatorColor = selectIndicatorColor,
                modifier = M
                    .fillMaxWidth()
                    .height(35.dp),
                margin = 28.dp,
            )
            ComposePager(
                pageCount = colors.size,
                modifier = M.fillMaxSize(),
                composePagerState = pagerState,
                orientation = Orientation.Horizontal,
            ) {
                Box(
                    modifier = M
                        .fillMaxSize()
                        .background(colors[index].first)
                )
            }
        }
    }
}