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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.lt.compose_views.text_field.GoodTextField
import com.lt.compose_views.text_field.HintComposeWithTextField
import com.lt.compose_views.text_field.PasswordTextField
import com.lt.test_compose.base.BaseComposeActivity
import com.lt.test_compose.base.M
import com.lt.test_compose.base.VerticalSpace

/**
 * creator: lt  2022/7/16  lt.dygzs@qq.com
 * effect :
 * warning:
 */
class TextFieldActivity : BaseComposeActivity() {
    override fun getTitleText(): String = "TextField"

    @Composable
    override fun ComposeContent() {
        Column(
            M
                .padding(20.dp)
                .width(200.dp)
        ) {
            val text1 = remember {
                mutableStateOf("我是账号")
            }
            var text2 by remember {
                mutableStateOf("")
            }
            val password = remember {
                mutableStateOf("password")
            }
            val passwordIsShow = remember {
                mutableStateOf(false)
            }
            val password2 = remember {
                mutableStateOf("123456")
            }
            val passwordIsShow2 = remember {
                mutableStateOf(true)
            }
            GoodTextField(
                value = text1.value,
                onValueChange = text1::value::set,
                modifier = M.height(40.dp)
            )
            VerticalSpace(dp = 16)
            GoodTextField(
                value = text2,
                onValueChange = { text2 = it },
                hint = remember {
                    HintComposeWithTextField.createTextHintCompose("请输入账号")
                },
                modifier = M.height(40.dp)
            )
            VerticalSpace(dp = 16)
            PasswordTextField(
                value = password.value,
                onValueChange = { password.value = it },
                passwordIsShow = passwordIsShow.value,
                onPasswordIsShowChange = { passwordIsShow.value = it },
                modifier = M.height(40.dp)
            )
            VerticalSpace(dp = 16)
            PasswordTextField(
                value = password2.value,
                onValueChange = { password2.value = it },
                passwordIsShow = passwordIsShow2.value,
                onPasswordIsShowChange = { passwordIsShow2.value = it },
                modifier = M.height(40.dp)
            )
        }
    }
}