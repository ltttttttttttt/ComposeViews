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

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.compose_views.banner.Banner
import com.lt.compose_views.banner.rememberBannerState
import com.lt.test_compose.base.BaseComposeActivity
import com.lt.test_compose.base.M

class BannerActivity : BaseComposeActivity() {
    val colors = listOf(
        Color(150, 105, 61, 255),
        Color(122, 138, 55, 255),
        Color(50, 134, 74, 255),
        Color(112, 62, 11, 255),
        Color(114, 61, 101, 255),
    )

    @Composable
    override fun ComposeContent() {
        val bannerState = rememberBannerState()
        val itemIndex by remember { bannerState.getCurrSelectIndexState() }
            .collectAsState(initial = 0)
        Banner(
            colors.size,
            M.fillMaxSize(),
            bannerState = bannerState,
            autoScrollTime = 1000,
            orientation = androidx.compose.foundation.gestures.Orientation.Vertical,
        ) {
            Box(
                modifier = M
                    .fillMaxSize()
                    .background(colors.getOrNull(index) ?: Color.Black)
            ) {
                Button(onClick = {
                    Toast.makeText(this@BannerActivity, "index=$index", Toast.LENGTH_SHORT).show()
                }, modifier = M.align(Alignment.Center)) {
                    Text(text = this@Banner.index.toString(), fontSize = 30.sp)
                }
            }
        }
        FpsMonitor(modifier = Modifier)
        Text(text = "item:$itemIndex", M.padding(start = 200.dp))
    }

}