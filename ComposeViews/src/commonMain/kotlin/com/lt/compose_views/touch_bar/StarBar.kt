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

package com.lt.compose_views.touch_bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lt.compose_views.util.runIf
import io.github.ltttttttttttt.composeviews.generated.resources.Res
import io.github.ltttttttttttt.composeviews.generated.resources.star_bar_star
import io.github.ltttttttttttt.composeviews.generated.resources.star_bar_star_select
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import kotlin.math.roundToInt

/**
 * creator: lt  2022/10/31  lt.dygzs@qq.com
 * effect : 星星选择和展示控件
 *          Star bar
 * warning:
 * @param starValue 当前选中的星星数量
 *                  Value of star with selected
 * @param onStarValueChange 选中的星星数量改变的回调
 *                          Callback of the [starValue] change
 * @param modifier 修饰
 * @param maxStar 最大星星数
 *                Star max value
 * @param starPainter 未选中的星星的图片
 *                    [Painter] of star
 * @param starSelectPainter 选中的星星的图片
 *                          [Painter] of star with selected
 * @param starSize 星星的大小
 *                 Size of star
 * @param margin 星星间的间距
 *               Distance between stars
 * @param orientation 滑动的方向
 *                    Scroll orientation
 * @param userEnable 用户是否可以滑动,等于false时用户滑动无反应,但代码可以执行翻页
 *                   Whether the user can scroll
 * @param onTouchUpEvent 手指抬起事件
 *                       Callback of touch up event
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun StarBar(
    starValue: Int,
    onStarValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    maxStar: Int = 5,
    starPainter: Painter = painterResource(Res.drawable.star_bar_star),
    starSelectPainter: Painter = painterResource(Res.drawable.star_bar_star_select),
    starSize: Dp = 16.dp,
    margin: Dp = 3.dp,
    orientation: Orientation = Orientation.Horizontal,
    userEnable: Boolean = true,
    onTouchUpEvent: (() -> Unit)? = null,
) {
    val singleStarPercentage by remember(maxStar) {
        mutableStateOf(1f / maxStar)
    }
    val starValueNumber by rememberUpdatedState(newValue = starValue)
    var progress by remember(maxStar) {
        mutableStateOf(starValueNumber * singleStarPercentage)
    }
    BasicsProgressBar(
        progress = progress,
        onProgressChange = {
            progress = it
            val star = (progress / singleStarPercentage).roundToInt()
            if (star != starValueNumber)
                onStarValueChange(star)
        },
        modifier = modifier,
        orientation = orientation,
        userEnable = userEnable,
        onTouchUpEvent = onTouchUpEvent,
    ) {
        @Composable
        fun Star() {
            repeat(maxStar) { index ->
                Image(
                    painter = if (starValue > index) starSelectPainter else starPainter,
                    contentDescription = "star",
                    modifier = Modifier
                        .size(starSize)
                        .runIf(userEnable) {
                            clickable(remember { MutableInteractionSource() }, null) {
                                onStarValueChange(index + 1)
                                onTouchUpEvent?.invoke()
                            }
                        },
                )
                if (index < maxStar - 1)
                    Spacer(modifier = Modifier.size(margin))
            }
        }

        if (orientation == Orientation.Horizontal)
            Row {
                Star()
            }
        else
            Column {
                Star()
            }
    }
}