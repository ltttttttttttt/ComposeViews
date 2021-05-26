package com.lt.test_compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.lt.test_compose.ui.theme.MyTheme
import com.lt.test_compose.ui.theme.Test_composeTheme
import com.lt.test_compose.ui.view.TitleView

class MainActivity : BaseComposeActivity() {
    @Composable
    override fun InitCompose() {
        // A surface container using the 'background' color from the theme
        Greeting("Android")
    }


    @Composable
    fun Greeting(name: String) {
        Column {
            TitleView("标题", "右边按钮")
            Text(text = "Hello $name!")
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
