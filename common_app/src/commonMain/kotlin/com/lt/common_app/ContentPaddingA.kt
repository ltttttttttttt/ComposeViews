package com.lt.common_app

import M
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lt.common_app.base.BaseComposeActivity
import com.lt.common_app.base.Shapes
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.rememberScalePagerContentTransformation

/**
 * creator: lt  2024/7/8  lt.dygzs@qq.com
 * effect :
 * warning:
 */
class ContentPaddingA : BaseComposeActivity() {
    @Composable
    override fun ComposeContent() {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            ComposePager(
                10,
                M.size(200.dp, 80.dp),
                orientation = Orientation.Horizontal,
                pageCache = 2,
                clip = false,
                contentTransformation = rememberScalePagerContentTransformation(0.8f)
            ) {
                Text(
                    text = "ContentPadding$index",
                    M.fillMaxSize().padding(horizontal = 10.dp).background(Color.Gray, Shapes.medium)
                )
            }
        }
    }
}