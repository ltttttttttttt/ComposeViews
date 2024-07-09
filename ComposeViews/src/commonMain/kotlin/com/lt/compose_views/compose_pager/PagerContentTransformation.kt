package com.lt.compose_views.compose_pager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import kotlin.math.abs

/**
 * creator: lt  2024/7/9  lt.dygzs@qq.com
 * effect : 变换ComposePager的Content
 *          Transform the Content of ComposePager
 * warning:
 */
@Immutable
interface PagerContentTransformation {

    /**
     * 通过state和scope来转换出content距离当前位置的百分比
     * @param state ComposePagerState
     * @param scope ComposePagerScope
     * @return Modifier
     */
    fun transformation(state: ComposePagerState, scope: ComposePagerScope, modifier: Modifier): Modifier {
        return transformation(state.getOffsetPercent(scope.index), modifier)
    }

    /**
     * 通过content距离当前位置的百分比变换ComposePager的Content
     * Transform the Content of ComposePager by the percentage of content distance from the current position
     */
    fun transformation(percent: Float, modifier: Modifier): Modifier
}

//不变换
internal object NoPagerContentTransformation : PagerContentTransformation {
    override fun transformation(state: ComposePagerState, scope: ComposePagerScope, modifier: Modifier): Modifier =
        modifier

    override fun transformation(percent: Float, modifier: Modifier): Modifier = modifier
}

/**
 * 缩放变换
 * Scale transformation
 * @property maxScale Float 最大的缩放值
 *                          Max scale value
 * @property minScale Float 最小的缩放值
 *                          Min scale value
 */
class ScalePagerContentTransformation(
    private val maxScale: Float,
    private val minScale: Float,
) : PagerContentTransformation {
    override fun transformation(percent: Float, modifier: Modifier): Modifier {
        val scale = (minScale - maxScale) * abs(percent) + maxScale
        return modifier.scale(scale)
    }
}

@Composable
fun rememberScalePagerContentTransformation(maxScale: Float, minScale: Float) =
    remember { ScalePagerContentTransformation(maxScale, minScale) }