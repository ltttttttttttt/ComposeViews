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

import android.content.Intent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.lt.test_compose.base.BaseComposeActivity

class MainActivity : BaseComposeActivity() {

    private val buttons = listOf(
        "rv的基础功能" to MainListActivity::class.java,
        "ViewPager功能" to ComposePagerActivity::class.java,
        "Banner功能" to BannerActivity::class.java,
        "FlowLayout功能" to FlowLayoutActivity::class.java,
    )

    override fun getTitleText(): String = "功能列表"

    @Composable
    override fun ComposeContent() {
        //startActivity(Intent(this@MainActivity, buttons[3].second))
        LazyColumn {
            items(buttons) {
                Button(onClick = { startActivity(Intent(this@MainActivity, it.second)) }) {
                    Text(text = it.first)
                }
            }
        }
    }
}
