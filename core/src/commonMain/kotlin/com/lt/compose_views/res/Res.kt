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
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter

/**
 * creator: lt  2022/11/13  lt.dygzs@qq.com
 * effect : 资源
 * warning: 由于目前的跨平台问题,导致只能依赖jvm平台,所以暂时不用kmp方式,而使用反射方式
 */
internal object Res {
    private const val imgFile = "drawable-xxhdpi/%s.png"
    private val isAndroid = "Dalvik" == System.getProperty("java.vm.name")
    private val localContext by lazy { getAndroidLocalContext() }
    private val stringClass = String::class.java

    //获取安卓的图片资源
    @Composable
    private fun getAndroidImagePainter(drawableName: String): Painter {
        val context = localContext.current
        return remember(drawableName, context) {
            getAndroidDrawablePainter(drawableName, context)
        }
    }

    //获取安卓的LocalContext
    private fun getAndroidLocalContext(): ProvidableCompositionLocal<Any> =
        Class.forName("androidx.compose.ui.platform.AndroidCompositionLocals_androidKt")
            .getMethod("getLocalContext")
            .invoke(null) as ProvidableCompositionLocal<Any>

    //通过drawable文件名获取Painter
    private fun getAndroidDrawablePainter(drawableName: String, context: Any): Painter {
        val contextClass = context.javaClass
        val res = contextClass.getMethod("getResources").invoke(context)
        val resClass = res.javaClass
        val contextPackageName = contextClass.getMethod("getPackageName").invoke(context) as String
        val drawableId = resClass.getMethod("getIdentifier", stringClass, stringClass, stringClass)
            .invoke(res, drawableName, "drawable", contextPackageName) as Int
        val bitmapDrawable =
            resClass.getMethod("getDrawable", Int::class.java).invoke(res, drawableId)
        val bitmap = bitmapDrawable.javaClass.getMethod("getBitmap").invoke(bitmapDrawable)
        val imageBitmap = Class.forName("androidx.compose.ui.graphics.AndroidImageBitmap_androidKt")
            .getMethod("asImageBitmap", bitmap.javaClass)
            .invoke(null, bitmap) as ImageBitmap
        return BitmapPainter(imageBitmap)
    }

    @Composable
    fun getRefreshLayoutLoadingPainter(): Painter =
        if (isAndroid) getAndroidImagePainter("compose_views_refresh_layout_loading") else Res2.getRefreshLayoutLoadingPainter()

    @Composable
    fun getNoMoreDataString(): String = "没有更多数据了"

    @Composable
    fun getLoadingString(): String = "正在加载中…"

    @Composable
    fun getRefreshLayoutArrowPainter(): Painter =
        if (isAndroid) getAndroidImagePainter("compose_views_refresh_layout_arrow") else Res2.getRefreshLayoutArrowPainter()

    @Composable
    fun getRefreshCompleteString(): String = "刷新完成"

    @Composable
    fun getRefreshingString(): String = "正在刷新…"

    @Composable
    fun getDropDownToRefreshString(): String = "下拉可以刷新"

    @Composable
    fun getReleaseRefreshNowString(): String = "释放立即刷新"

    @Composable
    fun getPasswordShowPainter(): Painter =
        if (isAndroid) getAndroidImagePainter("compose_views_password_show") else Res2.getPasswordShowPainter()

    @Composable
    fun getPasswordHidePainter(): Painter =
        if (isAndroid) getAndroidImagePainter("compose_views_password_hide") else Res2.getPasswordHidePainter()

    @Composable
    fun getStarSelectPainter(): Painter =
        if (isAndroid) getAndroidImagePainter("star_bar_star_select") else Res2.getStarSelectPainter()

    @Composable
    fun getStarPainter(): Painter =
        if (isAndroid) getAndroidImagePainter("star_bar_star") else Res2.getStarPainter()
}

internal expect object Res2 {
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