package com.lt.test_compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.rememberComposePagerState
import com.lt.test_compose.base.BaseComposeActivity
import kotlinx.coroutines.delay
import util.compose.M

class ComposePagerActivity : BaseComposeActivity() {
    val colors = listOf(
        Color(150, 105, 61, 255),
        Color(122, 138, 55, 255),
        Color(50, 134, 74, 255),
        Color(112, 62, 11, 255),
        Color(114, 61, 101, 255),
    )

    @Composable
    override fun InitCompose() {
        val composePagerState = rememberComposePagerState()
        LaunchedEffect(key1 = Unit, block = {
            while (true) {
                colors.withIndex().forEach {
                    composePagerState.currSelectIndex.value = it.index
                    delay(2000)
                }
            }
        })
        ComposePager(
            M
                .fillMaxWidth()
                .height(200.dp), composePagerState = composePagerState) {
            Box(
                modifier = M
                    .fillMaxSize()
                    .background(colors[index])
            )
            Text(text = index.toString(), M.align(Alignment.Center), fontSize = 30.sp)
        }
    }

}