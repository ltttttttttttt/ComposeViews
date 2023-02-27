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

package com.lt.compose_views.menu_fab

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.compose_views.other.HorizontalSpace


// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2022/7/23 15:34
// Description: 
// Documentation:

/**
 * 带菜单的Fab
 * Floating action button
 *
 * @param srcIcon 按钮的图标
 *                Menu icon
 * @param items 菜单项
 *              Menu items
 * @param modifier 修饰
 * @param srcIconColor 按钮图标的颜色
 *                     Color of button's icon
 * @param fabBackgroundColor 按钮区域背景色
 *                           Background color of floating action button
 * @param showLabels 是否展示菜单项的提示文本
 *                   Whether to display items
 * @param onFabItemClicked 菜单项点击事件
 *                         Click event of items
 */
@Composable
fun MenuFloatingActionButton(
    srcIcon: ImageVector,
    items: List<MenuFabItem>,
    modifier: Modifier = Modifier,
    menuFabState: MenuFabState = rememberMenuFabState(),
    srcIconColor: Color = Color.White,
    fabBackgroundColor: Color = Color.Unspecified,
    showLabels: Boolean = true,
    onFabItemClicked: (item: MenuFabItem) -> Unit
) {
    //创建过渡对象，用于管理多个动画值，并且根据状态变化运行这些值
    val transition = updateTransition(
        targetState = menuFabState.menuFabStateEnum.value,
        label = "menuFabStateEnum"
    )
    //用于+号按钮的旋转动画
    val rotateAnim: Float by transition.animateFloat(
        transitionSpec = {
            if (targetState == MenuFabStateEnum.Expanded) {
                spring(stiffness = Spring.StiffnessLow)
            } else {
                spring(stiffness = Spring.StiffnessMedium)
            }
        }, label = "rotateAnim"
    ) { state ->
        //根据state来设置最终的角度
        if (state == MenuFabStateEnum.Collapsed) 0F else -45F
    }
    //透明度动画
    val alphaAnim: Float by transition.animateFloat(transitionSpec = {
        tween(durationMillis = 200)
    }, label = "alphaAnim") { state ->
        if (state == MenuFabStateEnum.Expanded) 1F else 0F
    }
    Box(modifier = modifier, contentAlignment = Alignment.BottomEnd) {
        //创建多个Item,Fab按钮
        items.forEachIndexed { index, item ->
            //Item的收缩动画
            val shrinkAnim by transition.animateFloat(targetValueByState = { state ->
                when (state) {
                    MenuFabStateEnum.Collapsed -> 5F
                    //根据位置，递增每个item的位置高度
                    MenuFabStateEnum.Expanded -> (index + 1) * 60F + if (index == 0) 5F else 0F
                }
            }, label = "shrinkAnim", transitionSpec = {
                if (targetState == MenuFabStateEnum.Expanded) {
                    //dampingRatio属性删除等于默认1F，没有回弹效果
                    spring(stiffness = Spring.StiffnessLow, dampingRatio = 0.58F)
                } else {
                    spring(stiffness = Spring.StiffnessMedium)
                }
            })
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        //从收缩列表中获取
                        bottom = shrinkAnim.dp,
                        top = 5.dp,
                        end = 30.dp
                    )
                    .alpha(alphaAnim)
            ) {
                if (showLabels) {
                    Text(
                        item.label,
                        color = item.labelTextColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .alpha(alphaAnim)
                            .background(color = item.labelBackgroundColor)
                            .padding(start = 6.dp, end = 6.dp, top = 4.dp, bottom = 4.dp)
                    )
                    HorizontalSpace(16)
                }
                FloatingActionButton(
                    backgroundColor = if (item.fabBackgroundColor == Color.Unspecified) MaterialTheme.colors.primary else item.fabBackgroundColor,
                    modifier = Modifier.size(46.dp),
                    onClick = {
                        //更新状态 => 折叠菜单
                        menuFabState.menuFabStateEnum.value = MenuFabStateEnum.Collapsed
                        onFabItemClicked(item)
                    },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 2.dp,
                        pressedElevation = 4.dp
                    )
                ) {
                    item.icon()
                }
            }
        }
        //"+"号，切换按钮
        FloatingActionButton(
            modifier = Modifier.padding(0.dp, end = 25.dp),
            backgroundColor = if (fabBackgroundColor == Color.Unspecified) MaterialTheme.colors.primary else fabBackgroundColor,
            onClick = {
                //更新状态执行：收缩动画
                menuFabState.menuFabStateEnum.value =
                    if (menuFabState.menuFabStateEnum.value == MenuFabStateEnum.Collapsed) MenuFabStateEnum.Expanded else MenuFabStateEnum.Collapsed
            }) {
            Icon(
                imageVector = srcIcon,
                modifier = Modifier.rotate(rotateAnim),
                tint = srcIconColor,
                contentDescription = null
            )
        }
    }
}