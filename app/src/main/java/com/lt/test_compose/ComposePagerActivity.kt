package com.lt.test_compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.rememberComposePagerState
import com.lt.test_compose.base.BaseComposeActivity
import com.lt.test_compose.base.M

class ComposePagerActivity : BaseComposeActivity() {
    val colors = listOf(
        Color(150, 105, 61, 255),
        Color(122, 138, 55, 255),
        Color(50, 134, 74, 255),
        Color(112, 62, 11, 255),
        Color(114, 61, 101, 255),
    )

    @Composable
    override fun ComposeContent() {
        val composePagerState = rememberComposePagerState()
        ComposePager(
            colors.size,
            M
                .fillMaxWidth()
                .height(200.dp),
            composePagerState = composePagerState,
            //orientation = androidx.compose.foundation.gestures.Orientation.Vertical,
        ) {
            Box(
                modifier = M
                    .fillMaxSize()
                    .background(colors.getOrNull(index) ?: Color.Black)
            ) {
                Button(onClick = {
                    composePagerState.currSelectIndex.value = if (index + 1 >= colors.size)
                        0
                    else
                        index + 1
                }, modifier = M.align(Alignment.Center)) {
                    Text(text = this@ComposePager.index.toString(), fontSize = 30.sp)
                }
            }
        }
    }

}