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

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lt.compose_views.refresh_layout.RefreshContentStateEnum
import com.lt.compose_views.refresh_layout.RefreshableLazyColumn
import com.lt.compose_views.refresh_layout.rememberRefreshLayoutState
import com.lt.compose_views.util.rememberMutableStateOf
import com.lt.test_compose.base.BaseComposeActivity
import com.lt.test_compose.base.M
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

/**
 * creator: lt  2021/11/8  lt.dygzs@qq.com
 * effect :
 * warning:
 */
class MainListActivity : BaseComposeActivity() {
    private var array = ArrayList(IntArray(20) { it * 2 }.asList())
    private val random = Random()

    data class Value(val a: String, val b: String)

    override fun getTitleText(): String = "LazyColumn基础功能"

    @Composable
    override fun ComposeContent() {
        val list = remember {
            val list = mutableStateListOf<Int>()
            list.addAll(array)
            list
        }
        var bean by rememberMutableStateOf(value = Value("0", ""))
        Column {
            Divider()
            Log.e("lllttt", "${bean.a.length}")
            Text(
                text = "不动的text${bean.a}  b=${bean.b}",
                M
                    .clickable { bean = bean.copy(bean.a + "0", bean.b + "1") }
                    .verticalScroll(ScrollState(0))
            )
            ShowRv(list, {
                list.addAll(it)
            }, {
                list.clear()
                list.addAll(array)
            })
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun ShowRv(
        list: MutableList<Int>,
        listChangeListener: (List<Int>) -> Unit,
        reSetDataListener: () -> Unit,
    ) {
        val topRefreshLayoutState = rememberRefreshLayoutState(onRefreshListener = {
            mainScope.launch {
                delay(1500)
                Toast.makeText(this@MainListActivity, "刷新完成", Toast.LENGTH_LONG)
                    .show()
                setRefreshState(RefreshContentStateEnum.Stop)
                reSetDataListener()
            }
        })
        val bottomRefreshLayoutState = rememberRefreshLayoutState(onRefreshListener = {
            mainScope.launch {
                delay(1500)
                Toast.makeText(this@MainListActivity, "加载完成", Toast.LENGTH_LONG)
                    .show()
                setRefreshState(RefreshContentStateEnum.Stop)
                listChangeListener(IntArray(20) {
                    random.nextInt()
                }.asList())
            }
        })
        RefreshableLazyColumn(
            topRefreshLayoutState = topRefreshLayoutState,
            bottomRefreshLayoutState = bottomRefreshLayoutState,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = {
                stickyHeader {
                    Text(text = "粘懈标题")
                }
                item {
                    Text(text = "头布局")
                }
                stickyHeader {
                    Text(text = "粘懈标题2")
                }
                items(list) {
                    Text(
                        text = it.toString(),
                        M.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                }
                item {
                    Text(text = "尾布局,list.size=${list.size}")
                }
            })
    }

    @Preview
    @Composable
    fun PreView() {
        Surface {
            ComposeContent()
        }
    }
}