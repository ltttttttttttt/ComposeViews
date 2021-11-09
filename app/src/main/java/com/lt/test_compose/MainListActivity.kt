package com.lt.test_compose

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.lt.test_compose.ui.view.TitleView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import util.compose.M
import java.util.*
import kotlin.collections.ArrayList

/**
 * creator: lt  2021/11/8  lt.dygzs@qq.com
 * effect :
 * warning:
 */
class MainListActivity : BaseComposeActivity() {
    private var array = ArrayList<Int>(IntArray(20) { it * 2 }.asList())
    private var job: Job? = null
    private val random = Random()
    private val arrayLD = MutableLiveData(array)

    @Composable
    override fun InitCompose() {
//            var list by rememberMutableStateOf(value = array)
        var list by remember {
            mutableStateOf(array)
        }
        var string by remember {
            mutableStateOf("")
        }
//        val list = arrayLD.observeAsState()
        Column {
            TitleView(text = "rv")
            Divider()
            Text(text = "不动的text")
            ShowRv(list, string, { string = it }) {
                list = it
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun ShowRv(
        list: ArrayList<Int>,
        string: String,
        scl: (String) -> Unit,
        listChangeListener: (ArrayList<Int>) -> Unit
    ) {
        LazyColumn(// TODO by lt 2021/11/8 11:49 这里更新不了,需要改一下,然后加上自定义的下拉刷新
            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = {
                stickyHeader {
                    Text(text = "粘懈标题")
                }
                item {
                    Text(text = "头布局")
                }
                stickyHeader {
                    Text(text = "粘懈标题2")
                }
                items(list) {
                    Text(
                        text = it.toString(),
                        M.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                }
                item {
                    Text(text = "尾布局,list.size=${list.size}")
                }
                item {
                    //加载的布局
                    Text(text = "加载中$string")
                    if (job == null) {
                        job = mainScope.launch {
                            try {
                                delay(1000)
                                Toast.makeText(this@MainListActivity, "加载完成", Toast.LENGTH_LONG)
                                    .show()
                                array.addAll(IntArray(20) {
                                    random.nextInt()
                                }.asList())
                                listChangeListener(ArrayList(array))
                                scl("$string*")
                            } finally {
                                job = null
                            }
                        }
                    }
                }
            })
    }

    @Preview
    @Composable
    fun PreView() {
        Surface {
            InitCompose()
        }
    }
}