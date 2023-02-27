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
import androidx.compose.ui.graphics.painter.Painter

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

/**
 * 加载网络图片
 */
@Composable
actual fun rememberPainter(data: String?): Painter =
    com.lt.load_the_image.rememberImagePainter(url = data ?: "")

private const val IMG_FILE = "drawable-xxhdpi/%s.png"

/**
 * 根据图片文件名加载图片
 */
@Composable
actual fun resourcePainter(imageName: String): Painter =
    androidx.compose.ui.res.painterResource(IMG_FILE.format(imageName))

var _testIndex = 0

actual fun getTestIndex(): Int = _testIndex