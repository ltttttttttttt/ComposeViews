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

package com.lt.compose_views.res

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

/**
 * creator: lt  2022/11/13  lt.dygzs@qq.com
 * effect :
 * warning:
 */
internal expect object Res {
    @Composable
    fun getRefreshLayoutLoadingPainter(): Painter

    @Composable
    fun getRefreshLayoutArrowPainter(): Painter

    @Composable
    fun getPasswordShowPainter(): Painter

    @Composable
    fun getPasswordHidePainter(): Painter

    @Composable
    fun getStarSelectPainter(): Painter

    @Composable
    fun getStarPainter(): Painter

    @Composable
    fun getNoMoreDataString(): String

    @Composable
    fun getLoadingString(): String

    @Composable
    fun getRefreshCompleteString(): String

    @Composable
    fun getRefreshingString(): String

    @Composable
    fun getDropDownToRefreshString(): String

    @Composable
    fun getReleaseRefreshNowString(): String
}