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
import androidx.compose.ui.text.intl.Locale

/**
 * creator: lt  2022/11/13  lt.dygzs@qq.com
 * effect : 资源
 * warning:
 */
expect object Res {
    @Composable
    internal fun getRefreshLayoutLoadingPainter(): Painter

    @Composable
    internal fun getRefreshLayoutArrowPainter(): Painter

    @Composable
    internal fun getPasswordShowPainter(): Painter

    @Composable
    internal fun getPasswordHidePainter(): Painter

    @Composable
    internal fun getStarSelectPainter(): Painter

    @Composable
    internal fun getStarPainter(): Painter
}

//用户指定的语言
private var Res_language: String? = null

//获取当前是什么语言
private fun Res.getLanguage(): String {
    Res_language?.let { return it }
    return Locale.current.language
}

/**
 * 手动设置当前的语言
 */
fun Res.setLanguage(language: String) {
    Res_language = language
}


@Composable
internal fun Res.getNoMoreDataString(): String {
    return when (getLanguage()) {
        "zh" -> "没有更多数据了"
        else -> "No more data"
    }
}

@Composable
internal fun Res.getLoadingString(): String {
    return when (getLanguage()) {
        "zh" -> "正在加载中…"
        else -> "Loading"
    }
}

@Composable
internal fun Res.getRefreshCompleteString(): String {
    return when (getLanguage()) {
        "zh" -> "刷新完成"
        else -> "Refresh complete"
    }
}

@Composable
internal fun Res.getRefreshingString(): String {
    return when (getLanguage()) {
        "zh" -> "正在刷新…"
        else -> "Refreshing"
    }
}

@Composable
internal fun Res.getDropDownToRefreshString(): String {
    return when (getLanguage()) {
        "zh" -> "下拉可以刷新"
        else -> "Pull to refresh"
    }
}

@Composable
internal fun Res.getReleaseRefreshNowString(): String {
    return when (getLanguage()) {
        "zh" -> "释放立即刷新"
        else -> "Release refresh"
    }
}