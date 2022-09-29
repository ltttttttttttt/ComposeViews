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

package com.lt.compose_views.scrollable_appbar

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.lt.compose_views.util.ComposePosition
import kotlin.math.roundToInt

/**
 * creator: lt  2022/9/28  lt.dygzs@qq.com
 * effect : 链式(联动)滚动组件
 * warning:
 * @param minScrollPosition 最小滚动位置(距离指定方向的顶点)
 * @param maxScrollPosition 最大滚动位置(距离指定方向的顶点)
 * @param chainContent 链式(联动)滚动的compose组件,scrollOffset: 滚动位置(位于最小和最大之间)
 * @param modifier 修饰
 * @param composePosition 设置bar布局所在的位置,并且间接指定了滑动方向
 * @param chainMode 联动方式
 * @param isSupportCanNotScrollCompose 是否需要支持无法滚动的组件,为true的话内部会套一层可滚动组件
 * @param content compose内容区域
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChainScrollableComponent(
    minScrollPosition: Dp,
    maxScrollPosition: Dp,
    chainContent: @Composable (state: ChainScrollableComponentState) -> Unit,
    modifier: Modifier = Modifier,
    composePosition: ComposePosition = ComposePosition.Top,
    chainMode: ChainMode = ChainMode.ContentFirst,
    isSupportCanNotScrollCompose: Boolean = false,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val minPx = remember(key1 = minScrollPosition, key2 = density) {
        density.run { minScrollPosition.roundToPx() }
    }
    val maxPx = remember(key1 = maxScrollPosition, key2 = density) {
        density.run { maxScrollPosition.roundToPx() }
    }
    val coroutineScope = rememberCoroutineScope()
    val state = remember(key1 = minPx, key2 = maxPx, key3 = coroutineScope) {
        ChainScrollableComponentState(minPx, maxPx, coroutineScope)
    }
    val orientationIsHorizontal = remember(composePosition) {
        composePosition.isHorizontal()
    }
    //滚动监听,如果当前方向可以被优先使用,则不传手势给content
    val nestedScrollState = remember(
        composePosition,
        minScrollPosition,
        maxScrollPosition,
        chainMode,
    ) {
        when (chainMode) {
            ChainMode.ContentFirst -> ChainContentFirstNestedScrollConnection(
                state,
                composePosition
            )
            ChainMode.ChainContentFirst ->
                ChainContentFirstNestedScrollConnection(state, composePosition)
        }
    }
    Layout(
        content = {
            if (isSupportCanNotScrollCompose) {
                Box(
                    if (orientationIsHorizontal)
                        Modifier.horizontalScroll(rememberScrollState())
                    else
                        Modifier.verticalScroll(rememberScrollState())
                ) {
                    content()
                }
            } else {
                content()
            }
            Box(
                if (orientationIsHorizontal)
                    Modifier.horizontalScroll(rememberScrollState())
                else
                    Modifier.verticalScroll(rememberScrollState())
            ) {
                chainContent(state)
            }
        },
        modifier = modifier
            .nestedScroll(nestedScrollState)
            .clipScrollableContainer(composePosition.orientation)
    ) { measurableList, constraints ->
        val mConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val contentPlaceable = measurableList[0].measure(mConstraints)
        val chainContentPlaceable = measurableList[1].measure(
            if (orientationIsHorizontal)
                mConstraints.copy(maxWidth = maxPx)
            else
                mConstraints.copy(maxHeight = maxPx)
        )

        layout(contentPlaceable.width, contentPlaceable.height) {
            val offset = state.getScrollPositionValue().roundToInt()
            when (composePosition) {
                ComposePosition.Start -> {
                    contentPlaceable.placeRelative(offset, 0)
                    chainContentPlaceable.placeRelative(
                        (-chainContentPlaceable.width) + offset,
                        0
                    )
                }
                ComposePosition.End -> {
                    contentPlaceable.placeRelative(offset, 0)
                    chainContentPlaceable.placeRelative(
                        contentPlaceable.width + offset,
                        0
                    )
                }
                ComposePosition.Top -> {
                    contentPlaceable.placeRelative(0, offset)
                    chainContentPlaceable.placeRelative(
                        0,
                        (-chainContentPlaceable.height) + offset
                    )
                }
                ComposePosition.Bottom -> {
                    contentPlaceable.placeRelative(0, offset)
                    chainContentPlaceable.placeRelative(
                        0,
                        contentPlaceable.height + offset
                    )
                }
            }
        }
    }
}