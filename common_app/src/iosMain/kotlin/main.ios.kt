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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.ComposeUIViewController
import com.lt.common_app.base.BaseComposeActivity
import com.lt.common_app.base.MyTheme
import platform.UIKit.UIViewController

fun ComposeViewController(): UIViewController =
    ComposeUIViewController {
        MyTheme {
            Box(M.fillMaxSize()) {
                //activity
                val activity by
                remember { snapshotFlow { BaseComposeActivity._activityStack.lastOrNull() } }
                    .collectAsState(BaseComposeActivity._activityStack.lastOrNull())
                if (activity == null)
                    return@Box
                Column {
                    activity?.TitleView(activity?.getTitleText() ?: "")
                    activity!!.ComposeContent()
                }
                //toast
                AnimatedVisibility(
                    BaseComposeActivity._toastIsShow,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(M.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Box(
                            M.padding(horizontal = 40.dp)
                                .background(Color(0xfff1f1f1), RoundedCornerShape(20.dp))
                                .border(
                                    BorderStroke(2.dp, Color.White),
                                    RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 20.dp, vertical = 15.dp)
                                .animateContentSize()
                        ) {
                            Text(BaseComposeActivity._toast, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
