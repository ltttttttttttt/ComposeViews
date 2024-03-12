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

@file:JvmName("Res_common")

package com.lt.compose_views.res

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.intl.Locale
import kotlin.jvm.JvmName

/**
 * creator: lt  2022/11/13  lt.dygzs@qq.com
 * effect : 资源
 * warning:
 */
@Deprecated("Use Strings.kt")
typealias Res = Strings

object Strings {

    /**
     * 手动设置当前的语言
     */
    fun setLanguage(language: String) {
        Res_language = language
    }


    //用户指定的语言
    private var Res_language: String? by mutableStateOf(null)

    //获取当前是什么语言
    private fun getLanguage(): String {
        Res_language?.let { return it }
        return Locale.current.language
    }

    @Composable
    internal fun getNoMoreDataString(): String {
        return when (getLanguage()) {
            "zh" -> "没有更多数据了"
            else -> "No more data"
        }
    }

    @Composable
    internal fun getLoadingString(): String {
        return when (getLanguage()) {
            "zh" -> "正在加载中…"
            else -> "Loading"
        }
    }

    @Composable
    internal fun getRefreshCompleteString(): String {
        return when (getLanguage()) {
            "zh" -> "刷新完成"
            else -> "Refresh complete"
        }
    }

    @Composable
    internal fun getRefreshingString(): String {
        return when (getLanguage()) {
            "zh" -> "正在刷新…"
            else -> "Refreshing"
        }
    }

    @Composable
    internal fun getDropDownToRefreshString(): String {
        return when (getLanguage()) {
            "zh" -> "下拉可以刷新"
            else -> "Pull to refresh"
        }
    }

    @Composable
    internal fun getReleaseRefreshNowString(): String {
        return when (getLanguage()) {
            "zh" -> "释放立即刷新"
            else -> "Release refresh"
        }
    }
}