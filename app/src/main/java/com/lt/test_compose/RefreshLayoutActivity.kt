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

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lt.compose_views.flow_layout.FlowLayout
import com.lt.compose_views.refresh_layout.RefreshContentStateEnum
import com.lt.compose_views.refresh_layout.RefreshLayout
import com.lt.compose_views.refresh_layout.rememberRefreshLayoutState
import com.lt.compose_views.util.ComposePosition
import com.lt.test_compose.base.BaseComposeActivity
import com.lt.test_compose.base.M

class RefreshLayoutActivity : BaseComposeActivity() {
    private val composePosition = mutableStateOf(ComposePosition.Top)
    private val handler = Handler(Looper.getMainLooper())

    override fun getTitleText(): String = "RefreshLayout"

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun ComposeContent() {
        val refreshState = rememberRefreshLayoutState {
            runOnUiThread {
                Toast.makeText(this@RefreshLayoutActivity, "刷新了", Toast.LENGTH_SHORT).show()
            }
            handler.postDelayed({
                setRefreshState(RefreshContentStateEnum.Stop)
            }, 2000)
        }
        LaunchedEffect(key1 = Unit) {
            refreshState.setRefreshState(RefreshContentStateEnum.Refreshing)
        }

        Column(M.fillMaxSize()) {
            FlowLayout(horizontalMargin = 10.dp) {
                FpsMonitor(modifier = Modifier)
                Button(onClick = {
                    composePosition.value = when (composePosition.value) {
                        ComposePosition.Start -> ComposePosition.Top
                        ComposePosition.Top -> ComposePosition.End
                        ComposePosition.End -> ComposePosition.Bottom
                        ComposePosition.Bottom -> ComposePosition.Start
                    }
                }) {
                    Text(text = "改变刷新方向")
                }
                Text(text = "当前刷新方向:${composePosition.value}")
                Text(text = "当前刷新状态:${refreshState.getRefreshContentState().value}")
            }

            RefreshLayout(
                {
                    Text(text = "下拉刷新", modifier = M.background(Color.Red))
                },
                refreshLayoutState = refreshState,
                composePosition = composePosition.value,
            ) {
                Column(
                    modifier = M
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.Gray)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(text = "内容区域1")
                    Text(text = "内容区域2")
                    Text(text = "内容区域3")
                    Text(text = "内容区域4")
                    Text(text = "内容区域5")
                    Text(text = "内容区域6")
                    Text(text = "内容区域7")
                    Text(text = "内容区域8")
                    Text(text = "内容区域9")
                }
            }
        }
    }

}