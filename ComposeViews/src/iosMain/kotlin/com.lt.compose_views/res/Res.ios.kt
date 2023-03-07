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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

/**
 * creator: lt  2022/11/13  lt.dygzs@qq.com
 * effect : 资源
 * warning:
 */
internal actual object Res {
    private const val IMG_FILE_BEGIN = "drawable-xxhdpi/"
    private const val IMG_FILE_ENDING = ".png"

    /**
     * 根据图片文件名加载图片
     */
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun resourcePainter(imageName: String): Painter {
        return painterResource(remember(imageName) { IMG_FILE_BEGIN + imageName + IMG_FILE_ENDING })
    }

    @Composable
    actual fun getRefreshLayoutLoadingPainter(): Painter =
        resourcePainter("compose_views_refresh_layout_loading")

    @Composable
    actual fun getNoMoreDataString(): String = "没有更多数据了"

    @Composable
    actual fun getLoadingString(): String = "正在加载中…"

    @Composable
    actual fun getRefreshLayoutArrowPainter(): Painter =
        resourcePainter("compose_views_refresh_layout_arrow")

    @Composable
    actual fun getRefreshCompleteString(): String = "刷新完成"

    @Composable
    actual fun getRefreshingString(): String = "正在刷新…"

    @Composable
    actual fun getDropDownToRefreshString(): String = "下拉可以刷新"

    @Composable
    actual fun getReleaseRefreshNowString(): String = "释放立即刷新"

    @Composable
    actual fun getPasswordShowPainter(): Painter =
        resourcePainter("compose_views_password_show")

    @Composable
    actual fun getPasswordHidePainter(): Painter =
        resourcePainter("compose_views_password_hide")

    @Composable
    actual fun getStarSelectPainter(): Painter =
        resourcePainter("star_bar_star_select")

    @Composable
    actual fun getStarPainter(): Painter = resourcePainter("star_bar_star")
}