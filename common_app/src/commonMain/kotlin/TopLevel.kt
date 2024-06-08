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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

/**
 * creator: lt  2022/10/22  lt.dygzs@qq.com
 * effect :
 * warning:
 */

typealias M = Modifier

/**
 * 加载网络图片
 */
@Composable
expect fun rememberPainter(data: String?): Painter

/**
 * 根据图片文件名加载图片
 */
@Composable
expect fun resourcePainter(imageName: String): Painter

//获取测试页面索引
expect fun getTestIndex(): Int