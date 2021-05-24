package com.lt.test_compose.ui.view

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.test_compose.R
import com.lt.test_compose.ui.theme.MyTheme
import util.compose.h

/**
 * creator: lt  2021/5/23  lt.dygzs@qq.com
 * effect :
 * warning:
 */
@Composable
fun Activity.TitleView(
    text: String,
    rightText: String? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .h(45),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                ImageBitmap.imageResource(id = R.mipmap.back), "",
                Modifier
                    .clickable { finish() }
                    .fillMaxHeight()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp),
            )
            Spacer(modifier = Modifier.weight(1f))
            if (!rightText.isNullOrEmpty())
                Text(
                    text = rightText,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                )
        }
    }
}

@Preview
@Composable
private fun Activity.pTitleView() {
    MyTheme {
        TitleView("标题","右边按钮")
    }
}