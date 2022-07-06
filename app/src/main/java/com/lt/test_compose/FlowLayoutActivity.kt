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
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lt.compose_views.flow_layout.FlowLayout
import com.lt.test_compose.base.BaseComposeActivity
import com.lt.test_compose.base.HorizontalSpace
import com.lt.test_compose.base.M

/**
 * creator: lt  2022/7/4  lt.dygzs@qq.com
 * effect :
 * warning:
 */
class FlowLayoutActivity : BaseComposeActivity() {
    @Composable
    override fun ComposeContent() {
        FlowLayout(
            modifier = M
                .background(Color.Gray)
                .padding(10.dp)
        ) {
            Text(text = "0")
            HorizontalSpace(dp = 10)
            Text(text = "1111111111")
            HorizontalSpace(dp = 10)
            Text(text = "22222")
            HorizontalSpace(dp = 10)
            Text(text = "333333333333333333333333")
            HorizontalSpace(dp = 10)
            Image(painter = painterResource(id = R.mipmap.ic_launcher_test), contentDescription = "")
            HorizontalSpace(dp = 10)
            Text(text = "44444444444")
            HorizontalSpace(dp = 10)
            Text(text = "5555555\n5555")
            HorizontalSpace(dp = 10)
            Text(text = "666")
            HorizontalSpace(dp = 10)
            Text(text = "77777777777777777777777777777777777777777777")
            HorizontalSpace(dp = 10)
            Text(text = "8888888888888888888888")
            HorizontalSpace(dp = 10)
            Text(text = "9999999999999")
        }
    }
}