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

package com.lt.common_app

import M
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lt.common_app.base.BaseComposeActivity
import com.lt.compose_views.flow_layout.FlowLayout
import com.lt.compose_views.flow_layout.LabelsFlowLayout
import com.lt.compose_views.flow_layout.rememberLabelsFlowLayoutState
import com.lt.compose_views.util.SelectMode
import com.lt.compose_views.util.rememberMutableStateOf
import resourcePainter
import kotlin.random.Random

/**
 * creator: lt  2022/7/4  lt.dygzs@qq.com
 * effect :
 * warning:
 */
class FlowLayoutActivity : BaseComposeActivity() {
    private val items = mutableStateListOf(
        "0",
        "1111111111",
        "22222",
        "333333333333333333333333",
        "Image",
        "44444444444",
        "5555555\n5555",
        "666",
        "77777777777777777777777777777777777777777777",
    )
    private val orientation = mutableStateOf(Orientation.Horizontal)

    @Composable
    override fun ComposeContent() {
        Column {
            FlowLayout {
                Button(onClick = {
                    orientation.value = if (orientation.value == Orientation.Horizontal)
                        Orientation.Vertical
                    else
                        Orientation.Horizontal
                }) {
                    Text(text = "改变排列方向")
                }
                Text(text = "当前排列方向:${orientation.value}")
                Button(onClick = {
                    val sb = StringBuilder()
                    val i = Random.nextInt(20) + 1
                    repeat(i) {
                        sb.append(i.toString())
                    }
                    items.add(sb.toString())
                }) {
                    Text(text = "增加文字条目")
                }
                Button(onClick = {
                    items.add("Image")
                }) {
                    Text(text = "增加图片条目")
                }
                Button(onClick = {
                    items.removeFirstOrNull()
                }) {
                    Text(text = "删除第一个条目")
                }
                Button(onClick = {
                    items.removeLastOrNull()
                }) {
                    Text(text = "删除最后一个条目")
                }
            }

            FlowLayout(
                modifier = M
                    .background(Color.Gray)
                    .padding(10.dp),
                orientation = orientation.value,
                //horizontalAlignment = Alignment.CenterHorizontally,
                //verticalAlignment = Alignment.CenterVertically,
                horizontalMargin = 10.dp,
                verticalMargin = 10.dp,
                maxLines = 8
            ) {
                items.forEach {
                    if (it == "Image")
                        Image(
                            painter = resourcePainter("ic_launcher_test"),
                            contentDescription = ""
                        )
                    else
                        Text(text = it)
                }
            }

            var selectMode by rememberMutableStateOf<SelectMode>(value = SelectMode.Radio)
            val state = rememberLabelsFlowLayoutState(size = 10, selectMode = selectMode)
            Row {
                Button(onClick = { selectMode = SelectMode.Radio }) {
                    Text(text = "单选")
                }
                Button(onClick = { selectMode = SelectMode.MultipleChoice() }) {
                    Text(text = "多选")
                }
                Button(onClick = { selectMode = SelectMode.MultipleChoice(3) }) {
                    Text(text = "多选(最多3条)")
                }
                Button(onClick = {
                    state.selectData.toList().toString().showToast()
                }) {
                    Text(text = "获取数据")
                }
            }
            LabelsFlowLayout(size = 10, selectMode = selectMode, state = state) {
                Button(
                    onClick = {
                        //设置单选无法取消(也可以不加这个限制)
                        if (selectMode == SelectMode.Radio) {
                            setIsSelect(true) {}
                            return@Button
                        }
                        setIsSelect(!getIsSelect()) {
                            "超过选择上限了".showToast()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (getIsSelect()) Color.Yellow else MaterialTheme.colors.primary
                    )
                ) {
                    Text("$index")
                }
            }
        }
    }
}