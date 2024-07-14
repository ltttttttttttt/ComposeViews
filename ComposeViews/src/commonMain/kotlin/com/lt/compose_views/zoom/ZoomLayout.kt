package com.lt.compose_views.zoom

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import com.lt.compose_views.util.runIf
import com.lt.compose_views.util.midOf
import kotlin.math.roundToInt

/**
 * creator: lt  2023/10/9  lt.dygzs@qq.com
 * effect : 可以缩放的布局
 *          Zoom layout
 * warning:
 * @param modifier 修饰
 * @param alignment 对齐方式
 *                  Align mode
 * @param zoomRange 缩放的范围
 *                   Zoom range
 * @param zoomState [ZoomLayout]的状态
 *                   ZoomLayout's state
 * @param userCanRotation 用户是否可以旋转
 *                        Whether the user can rotate
 * @param whetherToLimitSize 是否限制内容大小
 *                           Whether to limit size
 * @param content compose内容区域
 *                Content of compose
 */
@Composable
fun ZoomLayout(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.TopStart,
    zoomRange: ClosedFloatingPointRange<Float> = 0.25f..4f,
    zoomState: ZoomState = rememberZoomState(),
    userCanRotation: Boolean = false,
    whetherToLimitSize: Boolean = false,
    //todo 增加边界功能:无限制,至少有一点在屏幕内,宽高至少一半在屏幕内,不允许出屏幕
    content: @Composable () -> Unit,
) {
    Box(modifier.clipToBounds()
        .pointerInput(zoomState) {
            //监听位移,缩放和旋转手势
            detectTransformGestures(true) { _, pan, zoom, rotation ->
                val newZoom = zoomState.zoom * zoom
                zoomState.zoom = midOf(zoomRange.start, newZoom, zoomRange.endInclusive)
                zoomState.offset += (pan / zoomState.zoom)
                if (userCanRotation) {
                    zoomState.rotation += rotation
                }
            }
        }
    ) {
        //不限制宽高,且多个控件叠加展示
        Layout(
            content, Modifier.fillMaxSize()
                .scale(zoomState.zoom)
                .offset {
                    IntOffset(
                        zoomState.offset.x.roundToInt(),
                        zoomState.offset.y.roundToInt()
                    )
                }
                .runIf(userCanRotation) {
                    rotate(zoomState.rotation)
                }) { measurableList, constraints ->
            var maxWidth = 0
            var maxHeight = 0
            val mConstraints = if (whetherToLimitSize) constraints else Constraints()
            val placeableList = measurableList.map {
                val placeable = it.measure(mConstraints)
                maxWidth = maxOf(maxWidth, placeable.width)
                maxHeight = maxOf(maxHeight, placeable.height)
                placeable
            }
            val layoutWidth =
                midOf(constraints.minWidth, maxWidth, constraints.maxWidth) * 2/*防止移出一定范围后直接消失*/
            val layoutHeight = midOf(constraints.minHeight, maxHeight, constraints.maxHeight) * 2
            layout(
                maxOf(maxWidth, constraints.maxWidth) * 2,
                maxOf(maxHeight, constraints.maxHeight) * 2
            ) {
                placeableList.forEach {
                    //自己处理align和宽高*2
                    val align = (alignment as? BiasAlignment)
                        ?: BiasAlignment(-1f, -1f)/*Alignment.TopStart*/
                    val x = when (align.horizontalBias) {
                        -1f -> if (layoutWidth / 2 >= it.width)/*父比子大*/ { layoutWidth / 4 } else { it.width - layoutWidth / 4 }
                        1f -> if (layoutWidth / 2 >= it.width) { layoutWidth / 4 + layoutWidth / 2 - it.width } else { layoutWidth / 4 }
                        else/*0f*/ -> if (layoutWidth / 2 >= it.width) { (layoutWidth - it.width) / 2 } else { it.width / 2 }
                    }
                    val y = when (align.verticalBias) {
                        -1f -> if (layoutHeight / 2 >= it.height) { layoutHeight / 4 } else { it.height - layoutHeight / 4 }
                        1f -> if (layoutHeight / 2 >= it.height) { layoutHeight / 4 + layoutHeight / 2 - it.height } else { layoutHeight / 4 }
                        else/*0f*/ -> if (layoutHeight / 2 >= it.height) { (layoutHeight - it.height) / 2 } else { it.height / 2 }
                    }
                    it.placeRelative(x, y)
                }
            }
        }
    }
}
