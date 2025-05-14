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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.common_app.base.BaseComposeActivity
import com.lt.common_app.base.click

/**
 * creator: lt  2021/5/23  lt.dygzs@qq.com
 * effect : 统一的标题栏
 * warning:
 */
private val Color333 = Color(0xFF333333)

@Composable
fun BaseComposeActivity.TitleView(
    text: String,
    rightTextAndClickListener: Pair<String, (() -> Unit)?>? = null,
    isShowBottomLine: Boolean = true,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color333,
            textAlign = TextAlign.Center,
        )
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Back",
                modifier = Modifier
                    .clickable { mFinish() }
                    .fillMaxHeight()
                    .wrapContentHeight()
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            if (rightTextAndClickListener != null)
                Text(
                    text = rightTextAndClickListener.first,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .click { rightTextAndClickListener.second?.invoke() }
                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                )
        }
        if (isShowBottomLine)
            Divider(M.align(Alignment.BottomCenter))
    }
}