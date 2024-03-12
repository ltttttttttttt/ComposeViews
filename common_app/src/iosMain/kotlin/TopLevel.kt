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
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private const val IMG_FILE_BEGIN = "drawable-xxhdpi/"
private const val IMG_FILE_ENDING = ".webp"

/**
 * 根据图片文件名加载图片
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun resourcePainter(imageName: String): Painter {
    return painterResource(remember(imageName) { DrawableResource(IMG_FILE_BEGIN + imageName + IMG_FILE_ENDING) })
}

actual fun getTestIndex(): Int {
    return -1
}