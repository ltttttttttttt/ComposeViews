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

package com.lt.compose_views.value_selector

import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.compose_views.other.VerticalSpace
import com.lt.compose_views.util.Color333
import com.lt.compose_views.util.rememberMutableStateListOf
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.round

/**
 * creator: lt  2022/12/3  lt.dygzs@qq.com
 * effect : 值选择器
 *          Value selector
 * warning:
 * @param values 值列表(值列表不支持重复值)
 *               Value list(Value list does not support duplicate values)
 * @param state ValueSelector的状态
 *              ValueSelector's state
 * @param modifier 修饰
 * @param defaultSelectIndex 默认选中的值索引
 *                           Default selected value index
 * @param isLoop 值列表是否可循环
 *               Whether the value list is loop
 * @param cacheSize 上下展示多少个额外的值,修改后需要同时修改[textSizes]和[textColors]
 *                  How many additional values are displayed up and down, After modification, you need to modify both [textSizes] and [textColors]
 * @param textSizes 未选中的字体大小列表
 *                  Text size list
 * @param selectedTextSize 选中的字体大小
 *                         Text size with selected
 * @param textColors 未选中的字体颜色列表
 *                   Text color list
 * @param selectedTextColor 选中的字体颜色
 *                          Text color with selected
 */
@ExperimentalFoundationApi
@Composable
fun ValueSelector(
    values: SnapshotStateList<String>,
    state: ValueSelectState,
    modifier: Modifier = Modifier,
    defaultSelectIndex: Int = 0,
    isLoop: Boolean = false,
    cacheSize: Int = 2,
    textSizes: SnapshotStateList<TextUnit> = rememberMutableStateListOf {
        listOf(
            valueSelector_defaultTextSize2,
            valueSelector_defaultTextSize1
        )
    },
    selectedTextSize: TextUnit = valueSelector_defaultSelectedTextSize,
    textColors: SnapshotStateList<Color> = rememberMutableStateListOf {
        listOf(
            valueSelector_defaultTextColor,
            valueSelector_defaultTextColor
        )
    },
    selectedTextColor: Color = valueSelector_defaultSelectedTextColor,
) {
    //init
    remember(defaultSelectIndex, state, values, cacheSize, isLoop, textSizes, textColors) {
        val selectIndex = if (state._lazyListState != null)
            state.getSelectIndex()
        else
            defaultSelectIndex
        state._lazyListState = LazyListState(
            if (isLoop)
                values.size * loopMultiple / 2 + selectIndex - cacheSize
            else
                selectIndex
        )
        state.cacheSize = cacheSize
        state.valueSize = values.size
        state.isLoop = isLoop
        if (textSizes.size != cacheSize || textColors.size != cacheSize)
            throw IllegalStateException("Size of [textSizes] and [textColors] must equals [cacheSize]")
    }
    val density = LocalDensity.current
    val itemHeight = remember(density) { density.run { 50.dp.toPx() } }
    val scope = rememberCoroutineScope()
    val scrollStopListener: NestedScrollConnection = remember {
        //用作衰减动画的衰减器,用于和速度一块计算最后能滑动到哪(当前速度能不能滑出去)
        val decay = splineBasedDecay<Float>(density)

        object : NestedScrollConnection {
            override suspend fun onPreFling(available: Velocity): Velocity {
                //根据衰减器和速度,来计算最后停下来的位置
                val targetOffsetX = decay.calculateTargetValue(
                    state.lazyListState.firstVisibleItemScrollOffset.toFloat(),
                    available.y
                )
                //计算速度大概能滚动多少条目,并执行滚动动画
                val itemNum = round(abs(targetOffsetX) / 4 / itemHeight).toInt()
                scope.launch {
                    if (available.y > 0) {
                        state.lazyListState.animateScrollToItem(
                            maxOf(
                                0,
                                state.lazyListState.firstVisibleItemIndex - itemNum
                            )
                        )
                    } else {
                        state.lazyListState.animateScrollToItem(
                            minOf(
                                values.size * loopMultiple,
                                state.lazyListState.firstVisibleItemIndex + itemNum
                            )
                        )
                    }
                }
                return available
            }
        }
    }
    ValueSelectorCompositionLocalProvider {
        Box(
            modifier.height(itemHeightDp * cacheSize * 2 + itemHeightDp)
                .fillMaxWidth()
                .nestedScroll(scrollStopListener)
        ) {
            LazyColumn(state = state.lazyListState, modifier = Modifier.fillMaxSize()) {
                val defaultTextAttributes = textSizes.last() to textColors.last()
                val itemFun: @Composable (index: Int, value: String) -> Unit = { index, value ->
                    val textAttributes by remember(state.lazyListState.firstVisibleItemIndex) {
                        val firstIndex = state.lazyListState.firstVisibleItemIndex
                        //计算text的大小和颜色
                        mutableStateOf(
                            if (firstIndex == index)
                                selectedTextSize to selectedTextColor
                            else {
                                //根据索引差值,从list中获取
                                val diff = abs(firstIndex - index)
                                if (diff >= cacheSize)
                                    defaultTextAttributes
                                else
                                    textSizes[diff - 1] to textColors[diff - 1]
                            }
                        )
                    }
                    Box(Modifier.fillMaxWidth().height(itemHeightDp)) {
                        Text(
                            value,
                            Modifier.align(Alignment.Center),
                            fontSize = textAttributes.first,
                            color = textAttributes.second,
                        )
                    }
                }
                if (isLoop) {
                    val valueSize = values.size
                    items(valueSize * loopMultiple, key = { it }) {
                        itemFun(it - cacheSize, remember(it) { values[it % valueSize] })
                    }
                } else {
                    repeat(cacheSize) {
                        item {
                            VerticalSpace(itemHeightDp)
                        }
                    }
                    itemsIndexed(values, key = { index, it -> it }) { index, value ->
                        itemFun(index, value)
                    }
                    repeat(cacheSize) {
                        item {
                            VerticalSpace(itemHeightDp)
                        }
                    }
                }
            }
        }
    }
}

@Deprecated("Need to use another function with the same name for higher performance")
@ExperimentalFoundationApi
@Composable
fun ValueSelector(
    values: ArrayList<String>,
    state: ValueSelectState,
    modifier: Modifier = Modifier,
    defaultSelectIndex: Int = 0,
    isLoop: Boolean = false,
    cacheSize: Int = 2,
    textSizes: ArrayList<TextUnit> = remember {
        arrayListOf(
            valueSelector_defaultTextSize2,
            valueSelector_defaultTextSize1
        )
    },
    selectedTextSize: TextUnit = valueSelector_defaultSelectedTextSize,
    textColors: ArrayList<Color> = remember {
        arrayListOf(
            valueSelector_defaultTextColor,
            valueSelector_defaultTextColor
        )
    },
    selectedTextColor: Color = valueSelector_defaultSelectedTextColor,
) {
    //init
    remember(defaultSelectIndex, state, values, cacheSize, isLoop, textSizes, textColors) {
        val selectIndex = if (state._lazyListState != null)
            state.getSelectIndex()
        else
            defaultSelectIndex
        state._lazyListState = LazyListState(
            if (isLoop)
                values.size * loopMultiple / 2 + selectIndex - cacheSize
            else
                selectIndex
        )
        state.cacheSize = cacheSize
        state.valueSize = values.size
        state.isLoop = isLoop
        if (textSizes.size != cacheSize || textColors.size != cacheSize)
            throw IllegalStateException("Size of [textSizes] and [textColors] must equals [cacheSize]")
    }
    val density = LocalDensity.current
    val itemHeight = remember(density) { density.run { 50.dp.toPx() } }
    val scope = rememberCoroutineScope()
    val scrollStopListener: NestedScrollConnection = remember {
        //用作衰减动画的衰减器,用于和速度一块计算最后能滑动到哪(当前速度能不能滑出去)
        val decay = splineBasedDecay<Float>(density)

        object : NestedScrollConnection {
            override suspend fun onPreFling(available: Velocity): Velocity {
                //根据衰减器和速度,来计算最后停下来的位置
                val targetOffsetX = decay.calculateTargetValue(
                    state.lazyListState.firstVisibleItemScrollOffset.toFloat(),
                    available.y
                )
                //计算速度大概能滚动多少条目,并执行滚动动画
                val itemNum = round(abs(targetOffsetX) / 4 / itemHeight).toInt()
                scope.launch {
                    if (available.y > 0) {
                        state.lazyListState.animateScrollToItem(
                            maxOf(
                                0,
                                state.lazyListState.firstVisibleItemIndex - itemNum
                            )
                        )
                    } else {
                        state.lazyListState.animateScrollToItem(
                            minOf(
                                values.size * loopMultiple,
                                state.lazyListState.firstVisibleItemIndex + itemNum
                            )
                        )
                    }
                }
                return available
            }
        }
    }
    ValueSelectorCompositionLocalProvider {
        Box(
            modifier.height(itemHeightDp * cacheSize * 2 + itemHeightDp)
                .fillMaxWidth()
                .nestedScroll(scrollStopListener)
        ) {
            LazyColumn(state = state.lazyListState, modifier = Modifier.fillMaxSize()) {
                val defaultTextAttributes = textSizes.last() to textColors.last()
                val itemFun: @Composable (index: Int, value: String) -> Unit = { index, value ->
                    val textAttributes by remember(state.lazyListState.firstVisibleItemIndex) {
                        val firstIndex = state.lazyListState.firstVisibleItemIndex
                        //计算text的大小和颜色
                        mutableStateOf(
                            if (firstIndex == index)
                                selectedTextSize to selectedTextColor
                            else {
                                //根据索引差值,从list中获取
                                val diff = abs(firstIndex - index)
                                if (diff >= cacheSize)
                                    defaultTextAttributes
                                else
                                    textSizes[diff - 1] to textColors[diff - 1]
                            }
                        )
                    }
                    Box(Modifier.fillMaxWidth().height(itemHeightDp)) {
                        Text(
                            value,
                            Modifier.align(Alignment.Center),
                            fontSize = textAttributes.first,
                            color = textAttributes.second,
                        )
                    }
                }
                if (isLoop) {
                    val valueSize = values.size
                    items(valueSize * loopMultiple, key = { it }) {
                        itemFun(it - cacheSize, remember(it) { values[it % valueSize] })
                    }
                } else {
                    repeat(cacheSize) {
                        item {
                            VerticalSpace(itemHeightDp)
                        }
                    }
                    itemsIndexed(values, key = { index, it -> it }) { index, value ->
                        itemFun(index, value)
                    }
                    repeat(cacheSize) {
                        item {
                            VerticalSpace(itemHeightDp)
                        }
                    }
                }
            }
        }
    }
}

internal val valueSelector_defaultTextSize1 = 14.sp
internal val valueSelector_defaultTextSize2 = 16.sp
internal val valueSelector_defaultSelectedTextSize = 18.sp
internal val valueSelector_defaultTextColor = Color333
internal val valueSelector_defaultSelectedTextColor = Color(0xff0D8AFF)
private val itemHeightDp = 41.dp
private const val loopMultiple = 10000

@Composable
internal expect fun ValueSelectorCompositionLocalProvider(content: @Composable () -> Unit)