package com.lt.compose_views.value_selector

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lt.compose_views.other.VerticalSpace

/**
 * creator: lt  2022/12/7  lt.dygzs@qq.com
 * effect : 时间选择器
 *          Time Selector
 * warning:
 * @param valuesList 数据列表
 *                   Values
 * @param states 状态列表
 *               State list
 * @param titleContent 标题部分的布局
 *                     Title with compose
 * @param modifier 修饰
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimeSelector(
    valuesList: List<ArrayList<String>>,
    states: List<ValueSelectState>,
    titleContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        titleContent()
        Box {
            Row {
                repeat(valuesList.size) {
                    ValueSelector(
                        remember(valuesList, it) { valuesList[it] },
                        remember(states, it) { states[it] },
                        Modifier.weight(1f)
                    )
                }
            }
            Column(Modifier.fillMaxWidth().align(Alignment.Center)) {
                Divider(Modifier.fillMaxWidth().height(1.dp), lineColor)
                VerticalSpace(48)
                Divider(Modifier.fillMaxWidth().height(1.dp), lineColor)
            }
        }
    }
}

private val lineColor = Color(0xffe6e6e6)