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

package com.lt.common_app

import M
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.lt.common_app.base.BaseComposeActivity
import com.lt.compose_views.value_selector.ValueSelector
import com.lt.compose_views.value_selector.rememberValueSelectState

/**
 * creator: lt  2022/12/3  lt.dygzs@qq.com
 * effect :
 * warning:
 */
class TimeSelectorA : BaseComposeActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun ComposeContent() {
        Column(M.fillMaxSize()) {
            ValueSelector(
                values = remember { (0 until 60).map(Int::toString) },
                state = rememberValueSelectState(),
                defaultSelectIndex = 10,
                //isLoop = true,
                //cacheSize = 4,
                //textColors = arrayListOf(
                //    Color.Black, Color.Red, Color.Green, Color.Blue
                //),
                //textSizes = arrayListOf(
                //    16.sp, 14.sp, 12.sp, 10.sp,
                //)
            )
        }
    }
}