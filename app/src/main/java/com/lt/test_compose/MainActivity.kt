package com.lt.test_compose

import android.view.View
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lt.test_compose.ui.theme.MyTheme
import com.lt.test_compose.ui.view.TitleView
import util.compose.HorizontalSpace
import util.compose.M
import util.compose.VerticalSpace
import util.compose.rememberMutableStateOf

class MainActivity : BaseComposeActivity() {
    val title = MutableLiveData("学情报告")

    @Composable
    override fun InitCompose() {
        // A surface container using the 'background' color from the theme
//        Greeting("Android")
        XueQing(title)
        findViewById<View>(android.R.id.content).postDelayed({
            title.value = "首测报告"
        }, 9000)
    }

    @Composable
    fun XueQing(title: LiveData<String>) {
        val title by title.observeAsState()
        Column {
            TitleView(text = title?:"")
            Row {
                HorizontalSpace(dp = 31)
                Column(
                    M.fillMaxWidth()
                ) {
                    Text(
                        text = "最近一次学案作答分析",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    VerticalSpace(dp = 15)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Spacer(
                            modifier = Modifier
                                .width(4.dp)
                                .height(18.dp)
                                .background(Color.Black)
                        )
                        HorizontalSpace(dp = 8)
                        Text(
                            text = "课前测",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    VerticalSpace(dp = 15)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.mipmap.img1),
                            contentDescription = "",
                            M.size(27.dp)
                        )
                        HorizontalSpace(dp = 8)
                        Text(text = "本次测试不认识单词(共计3个)", fontSize = 17.sp)
                    }
                    VerticalSpace(dp = 9)
                    LazyColumn(
                        Modifier.border(
                            border = BorderStroke(
                                width = 3.dp,
                                color = Color(0xFF999999),
                            ),
                            shape = RoundedCornerShape(2f, 2f, 2f, 2f),
                        )//边框
                    ) {
                        items(30) {
                            Column {
                                if (it != 0)
                                    Spacer(
                                        modifier = M
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(Color.Black)
                                    )
                                Row(
                                    M.height(IntrinsicSize.Min),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "序号",
                                        M.weight(153f),
                                        textAlign = TextAlign.Center,
                                    )
                                    Spacer(
                                        modifier = M
                                            .width(1.dp)
                                            .fillMaxHeight()
                                            .background(Color.Black)
                                    )
                                    Text(
                                        text = "单词",
                                        M.weight(219f),
                                        textAlign = TextAlign.Center,
                                    )
                                    Spacer(
                                        modifier = M
                                            .width(1.dp)
                                            .fillMaxHeight()
                                            .background(Color.Black)
                                    )
                                    Text(
                                        text = "音标",
                                        M.weight(219f),
                                        textAlign = TextAlign.Center,
                                    )
                                    Spacer(
                                        modifier = M
                                            .width(1.dp)
                                            .fillMaxHeight()
                                            .background(Color.Black)
                                    )
                                    Text(
                                        text = "发音",
                                        M.weight(219f),
                                        textAlign = TextAlign.Center,
                                    )
                                    Spacer(
                                        modifier = M
                                            .width(1.dp)
                                            .fillMaxHeight()
                                            .background(Color.Black)
                                    )
                                    Text(
                                        text = "词性",
                                        M.weight(219f),
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }

                        }
                    }
                }
                HorizontalSpace(dp = 31)
            }
        }
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
//            Greeting("Android")
            XueQing(title)
        }
    }
}
