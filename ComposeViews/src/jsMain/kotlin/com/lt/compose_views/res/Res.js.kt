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

package com.lt.compose_views.res

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

/**
 * creator: lt  2022/11/13  lt.dygzs@qq.com
 * effect : 资源
 * warning:
 */

private const val IMG_FILE_BEGIN = "drawable-xxhdpi/"
private const val IMG_FILE_ENDING = ".webp"

@OptIn(ExperimentalResourceApi::class)
@Composable
internal actual fun resourcePainter(name: String): Painter =
    painterResource(remember(name) { DrawableResource(IMG_FILE_BEGIN + name + IMG_FILE_ENDING) })