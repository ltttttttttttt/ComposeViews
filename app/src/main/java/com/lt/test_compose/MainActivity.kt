package com.lt.test_compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lt.test_compose.ui.theme.MyTheme
import com.lt.test_compose.ui.view.TitleView

class MainActivity : BaseComposeActivity() {
    @Composable
    override fun InitCompose() {
        // A surface container using the 'background' color from the theme
        Greeting("Android")
    }


    @Composable
    fun Greeting(name: String) {
        Surface(
            shape = MaterialTheme.shapes.medium, // 使用 MaterialTheme 自带的形状
            elevation = 5.dp,
            modifier = Modifier.padding(all = 8.dp),
        ) {
            Column {
                TitleView("标题", "右边按钮")
                Text(
                    text = "Hello $name!",
                    color = MaterialTheme.colors.secondaryVariant, // 添加颜色
                    style = MaterialTheme.typography.h2, // 添加 style
                ) // 将图片裁剪成圆形
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background), "",
                    Modifier
                        .size(200.dp)
                        .clip(CircleShape) // 将图片裁剪成圆形
                        .border(1.5.dp, MaterialTheme.colors.secondary, shape = CircleShape) // 添加边框
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
//        Test_composeTheme {
        MyTheme {
            Greeting("Android")
        }
    }
}
