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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

/**
 * creator: lt  2022/11/13  lt.dygzs@qq.com
 * effect :
 * warning:
 */
actual object Res {
    private const val imgFile = "drawable-xxhdpi/%s.png"

    @Composable
    internal actual fun getRefreshLayoutLoadingPainter(): Painter =
        painterResource(imgFile.format("compose_views_refresh_layout_loading"))

    @Composable
    internal actual fun getRefreshLayoutArrowPainter(): Painter =
        painterResource(imgFile.format("compose_views_refresh_layout_arrow"))

    @Composable
    internal actual fun getPasswordShowPainter(): Painter =
        painterResource(imgFile.format("compose_views_password_show"))

    @Composable
    internal actual fun getPasswordHidePainter(): Painter =
        painterResource(imgFile.format("compose_views_password_hide"))

    @Composable
    internal actual fun getStarSelectPainter(): Painter =
        painterResource(imgFile.format("star_bar_star_select"))

    @Composable
    internal actual fun getStarPainter(): Painter =
        painterResource(imgFile.format("star_bar_star"))
}