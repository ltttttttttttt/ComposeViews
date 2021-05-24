package util.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable

/**
 * creator: lt  2021/4/13  lt.dygzs@qq.com
 * effect : 简单的常用的控件
 * warning:
 */

/**
 * 间隔view
 */
@Composable
fun HorizontalSpace(dp: Int) {
    Spacer(w(dp))
}

@Composable
fun VerticalSpace(dp: Int) {
    Spacer(h(dp))
}