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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lt.compose_views.chain_scrollable_component.swipe_to_dismiss.SwipeToDismiss
import com.lt.compose_views.other.VerticalSpace
import com.lt.compose_views.touch_bar.StarBar
import com.lt.compose_views.util.rememberMutableStateOf
import com.lt.test_compose.base.BaseComposeActivity

/**
 * creator: lt  2022/10/2  lt.dygzs@qq.com
 * effect :
 * warning:
 */
class SwipeToDismissActivity : BaseComposeActivity() {
    private val width = 160.dp
    private val height = 80.dp

    @Composable
    override fun ComposeContent() {
        Column(modifier = Modifier.padding(start = 20.dp)) {
            Text("system:")
            SystemSwipeToDismiss()
            VerticalSpace(dp = 20)
            Text("composeViews:")
            ComposeViewsSwipeToDismiss(true)
            VerticalSpace(dp = 5)
            ComposeViewsSwipeToDismiss(false)
            VerticalSpace(dp = 5)
            Star()
        }
    }

    @Composable
    private fun Star() {
        var star by rememberMutableStateOf(value = 0)
        StarBar(
            starValue = star,
            onStarValueChange = { star = it },
            onTouchUpEvent = remember { { Log.e("lllttt", "star=$star") } },
        )
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun ComposeViewsSwipeToDismiss(contentIsMove: Boolean) {
        SwipeToDismiss(
            minScrollPosition = 0.dp,
            maxScrollPosition = width / 2,
            backgroundContent = {
                Text(
                    text = "删除",
                    Modifier
                        .fillMaxWidth()
                        .height(height)
                        .background(Color.Red)
                    //.clickable {
                    //    Toast
                    //        .makeText(this@SwipeToDismissActivity, "删除", Toast.LENGTH_SHORT)
                    //        .show()
                    //}
                )
            },
            contentIsMove = contentIsMove,
        ) {
            Text(
                text = "content", modifier = Modifier
                    .size(width, height)
                    .background(Color.LightGray)
            )
        }
    }

    @Composable
    @OptIn(ExperimentalMaterialApi::class)
    private fun SystemSwipeToDismiss() {
        val state = rememberDismissState()
        androidx.compose.material.SwipeToDismiss(state = state, background = {
            Text(
                text = "background", modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            )
        }, modifier = Modifier.size(width, height)) {
            Text(
                text = "content", modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            )
        }
    }
}