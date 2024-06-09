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

package com.lt.common_app

import M
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.lt.common_app.base.BaseComposeActivity
import com.lt.compose_views.other.VerticalSpace
import com.lt.compose_views.text_field.GoodTextField
import com.lt.compose_views.text_field.HintComposeWithTextField
import com.lt.compose_views.text_field.PasswordTextField
import com.lt.compose_views.util.rememberMutableStateOf

/**
 * creator: lt  2022/7/16  lt.dygzs@qq.com
 * effect :
 * warning:
 */
class TextFieldActivity : BaseComposeActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun ComposeContent() {
        Column(
            M
                .padding(20.dp)
                .width(200.dp)
        ) {
            //进入页面的时候自动弹出键盘
            val keyboardController = LocalSoftwareKeyboardController.current//软键盘控制器
            val focusRequester = FocusRequester()//焦点控制器
            LaunchedEffect(key1 = Unit, block = {
                focusRequester.requestFocus()//使其获取焦点
                keyboardController?.show()//弹出软键盘
            })

            val text1 = rememberMutableStateOf { "我是账号" }
            var text2 by rememberMutableStateOf { "" }
            val password = rememberMutableStateOf { "password" }
            val passwordIsShow = rememberMutableStateOf { false }
            val password2 = rememberMutableStateOf { "123456" }
            val passwordIsShow2 = rememberMutableStateOf { true }
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
            VerticalSpace(dp = 16)
            Divider()
            VerticalSpace(dp = 16)
            Text("TextFieldValue")
            VerticalSpace(dp = 16)
            val fieldText = rememberMutableStateOf {
                TextFieldValue("我是账号", TextRange(4))
            }
            val fieldPassword = rememberMutableStateOf { TextFieldValue("password", TextRange(6)) }
            val fieldPasswordIsShow = rememberMutableStateOf { false }
            GoodTextField(
                value = fieldText.value,
                onValueChange = fieldText::value::set,
                hint = remember {
                    HintComposeWithTextField.createTextHintCompose("请输入账号")
                },
                modifier = M.height(40.dp).focusRequester(focusRequester)
            )
            VerticalSpace(dp = 16)
            PasswordTextField(
                value = fieldPassword.value,
                onValueChange = { fieldPassword.value = it },
                passwordIsShow = fieldPasswordIsShow.value,
                onPasswordIsShowChange = { fieldPasswordIsShow.value = it },
                modifier = M.height(40.dp)
            )
        }
    }
}