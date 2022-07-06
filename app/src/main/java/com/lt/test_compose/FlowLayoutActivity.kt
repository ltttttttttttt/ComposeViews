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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lt.compose_views.flow_layout.FlowLayout
import com.lt.test_compose.base.BaseComposeActivity
import com.lt.test_compose.base.M
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
        "8888888888888888888888",
        "9999999999999",
    )
    private val orientation = mutableStateOf(Orientation.Horizontal)

    override fun getTitleText(): String = "FlowLayout"

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
                    val i = Random.nextInt(20)
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
            ) {
                items.forEach {
                    if (it == "Image")
                        Image(
                            painter = painterResource(id = R.mipmap.ic_launcher_test),
                            contentDescription = ""
                        )
                    else
                        Text(text = it)
                }
            }
        }
    }
}