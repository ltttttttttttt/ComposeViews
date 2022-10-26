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

package com.lt.compose_views.menu_fab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/7/23 15:41
// Description: 
// Documentation:

/**
 * 菜单的状态
 * State of the [MenuFloatingActionButton]
 */
class MenuFabState {
    /**
     * 菜单内部的展开折叠状态
     * State of the [MenuFloatingActionButton]
     */
    val menuFabStateEnum: MutableState<MenuFabStateEnum> =
        mutableStateOf(MenuFabStateEnum.Collapsed)
}

/**
 * 创建一个[remember]的[MenuFabState]
 * Create the [MenuFabState] of [remember]
 */
@Composable
fun rememberMenuFabState() = remember { MenuFabState() }