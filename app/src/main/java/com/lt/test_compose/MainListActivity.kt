package com.lt.test_compose

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lt.test_compose.ui.view.TitleView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import util.compose.M
import util.compose.rememberMutableStateOf
import java.util.*
import kotlin.collections.ArrayList

/**
 * creator: lt  2021/11/8  lt.dygzs@qq.com
 * effect :
 * warning:
 */
class MainListActivity : BaseComposeActivity() {
    private var array = ArrayList<Int>(IntArray(50) { it * 2 }.asList())
    private var job: Job? = null
    private val random = Random()

    @Composable
    override fun InitCompose() {
        Column {
            var list by rememberMutableStateOf(value = array)
            TitleView(text = "rv")
            Divider()
            Text(text = "不动的text")
            LazyColumn(// TODO by lt 2021/11/8 11:49 这里更新不了,需要改一下,然后加上自定义的下拉刷新 
                verticalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    item {
                        Text(text = "头布局")
                    }
                    val size = list.size
                    items(size) {
                        Text(
                            text = list[it].toString(),
                            M.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                        )
                    }
                    item {
                        Text(text = "尾布局")
                    }
                    item {
                        //加载的布局
                        Text(text = "加载中")
                        if (job == null) {
                            job = mainScope.launch {
                                try {
                                    delay(3000)
                                    array.addAll(IntArray(20) {
                                        random.nextInt()
                                    }.asList())
                                    list = array
                                    Log.e("lllttt", array.toString())
                                    Log.e("lllttt2", list.toString())
                                } finally {
                                    job = null
                                }
                            }
                        }
                    }
                })
        }
    }

    @Preview
    @Composable
    fun PreView() {
        Surface {
            InitCompose()
        }
    }
}