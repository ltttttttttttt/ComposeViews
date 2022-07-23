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

package com.lt.compose_views.text_field

import androidx.annotation.IntRange
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.compose_views.util.Color333

/**
 * creator: lt  2022/7/16  lt.dygzs@qq.com
 * effect : 更方便易用的TextField(文本输入框)
 * warning:
 * [value]输入框中的文字
 * [onValueChange]输入框中文字的变化回调
 * [modifier]修饰
 * [hint]输入框没有文字时展示的内容
 * [maxLines]最多能展示多少行文字
 * [fontSize]text和hint的字体大小
 * [fontColor]text的字体颜色
 * [maxLength]最多能展示多少个文字,ps:由于会截断文字,会导致截断时重置键盘状态(TextField特性)
 * [contentAlignment]text和hint对其方式
 * [leading]展示在左边的组件
 * [trailing]展示在右边的组件
 * [background]背景
 * [horizontalPadding]横向的内间距
 * [enabled]是否可输入,false无法输入和复制
 * [readOnly]是否可输入,true无法输入,但可复制,获取焦点,移动光标
 * [textStyle]字体样式
 * [keyboardOptions]键盘配置
 * [keyboardActions]键盘回调
 * [visualTransformation]文本展示的转换
 * [onTextLayout]计算新文本布局时执行的回调
 * [interactionSource]状态属性
 * [cursorBrush]光标绘制
 */
@Composable
fun GoodTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: HintComposeWithTextField? = null,
    @IntRange(from = 1L) maxLines: Int = 1,
    fontSize: TextUnit = 16.sp,
    fontColor: Color = Color333,
    maxLength: Int = Int.MAX_VALUE,
    contentAlignment: Alignment.Vertical = Alignment.CenterVertically,
    leading: (@Composable RowScope.() -> Unit)? = null,
    trailing: (@Composable RowScope.() -> Unit)? = null,
    background: BackgroundComposeWithTextField? = BackgroundComposeWithTextField.DEFAULT,
    horizontalPadding: Dp = 16.dp,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(MaterialTheme.colors.primary),
) {
    //使用基础输入框
    BasicTextField(
        value = value,
        onValueChange = {
            onValueChange(if (it.length > maxLength) it.substring(0, maxLength) else it)
        },
        textStyle = textStyle.copy(color = fontColor, fontSize = fontSize),
        singleLine = maxLines == 1,
        maxLines = maxLines,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        decorationBox = {
            //放置背景等布局,并放置基础输入框
            Box(
                Modifier
                    .height(IntrinsicSize.Min)
                    .let {
                        background?.setBackground(it) ?: it
                    }
                    .padding(horizontal = horizontalPadding),
            ) {
                Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                    if (leading != null) {
                        leading()
                        Spacer(Modifier.width(horizontalPadding))
                    }
                    Box(
                        Modifier
                            .weight(1f)
                            .align(contentAlignment)
                            .let {
                                if (contentAlignment != Alignment.CenterVertically)
                                    it.padding(vertical = horizontalPadding / 2)
                                else
                                    it
                            }
                    ) {
                        if (value.isEmpty() && hint != null)
                            hint.Hint(fontSize = fontSize)
                        it()
                    }
                    if (trailing != null) {
                        Spacer(Modifier.width(horizontalPadding))
                        trailing()
                    }
                }
            }
        }
    )
}