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
internal actual object Res2 {
    @Composable
    actual fun getRefreshLayoutLoadingPainter(): Painter = null!!
    //painterResource(id = R.drawable.compose_views_refresh_layout_loading)

    @Composable
    actual fun getNoMoreDataString(): String = null!!
    //stringResource(R.string.compose_views_no_more_data)

    @Composable
    actual fun getLoadingString(): String = null!!
    //stringResource(R.string.compose_views_loading)

    @Composable
    actual fun getRefreshLayoutArrowPainter(): Painter = null!!
    //painterResource(id = R.drawable.compose_views_refresh_layout_arrow)

    @Composable
    actual fun getRefreshCompleteString(): String = null!!
    //stringResource(id = R.string.compose_views_refresh_complete)

    @Composable
    actual fun getRefreshingString(): String = null!!
    //stringResource(id = R.string.compose_views_refreshing)

    @Composable
    actual fun getDropDownToRefreshString(): String = null!!
    //stringResource(id = R.string.compose_views_drop_down_to_refresh)

    @Composable
    actual fun getReleaseRefreshNowString(): String = null!!
    //stringResource(id = R.string.compose_views_release_refresh_now)

    @Composable
    actual fun getPasswordShowPainter(): Painter = null!!
    //painterResource(id = R.drawable.compose_views_password_show)

    @Composable
    actual fun getPasswordHidePainter(): Painter = null!!
    //painterResource(id = R.drawable.compose_views_password_hide)

    @Composable
    actual fun getStarSelectPainter(): Painter = null!!
    //painterResource(id = R.drawable.star_bar_star_select)

    @Composable
    actual fun getStarPainter(): Painter = null!!
    //painterResource(id = R.drawable.star_bar_star)
}