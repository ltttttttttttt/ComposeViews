package com.lt.test_compose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.lt.compose_views.banner.Banner
import com.lt.test_compose.base.BaseComposeActivity
import com.lt.test_compose.base.M

class BannerActivity : BaseComposeActivity() {
    val colors = listOf(
        Color(150, 105, 61, 255),
        Color(122, 138, 55, 255),
        Color(50, 134, 74, 255),
        Color(112, 62, 11, 255),
        Color(114, 61, 101, 255),
    )

    @Composable
    override fun ComposeContent() {
        Banner(
            colors.size,
            M.fillMaxSize(),
            autoScrollTime = 1000,
            orientation = androidx.compose.foundation.gestures.Orientation.Vertical,
        ) {
            Box(
                modifier = M
                    .fillMaxSize()
                    .background(colors.getOrNull(index) ?: Color.Black)
            ) {
                Button(onClick = {
                    Toast.makeText(this@BannerActivity, "index=$index", Toast.LENGTH_SHORT).show()
                }, modifier = M.align(Alignment.Center)) {
                    Text(text = this@Banner.index.toString(), fontSize = 30.sp)
                }
            }
        }
        FpsMonitor(modifier = Modifier)
    }

}