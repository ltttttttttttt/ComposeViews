package com.lt.test_compose

import android.content.Intent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.lt.test_compose.base.BaseComposeActivity

class MainActivity : BaseComposeActivity() {
    private val buttons = listOf(
        "rv的基础功能" to MainListActivity::class.java,
        "ViewPager功能" to MainListActivity::class.java,
    )

    @Composable
    override fun InitCompose() {
        LazyColumn {
            items(buttons) {
                Button(onClick = { startActivity(Intent(this@MainActivity, it.second)) }) {
                    Text(text = it.first)
                }
            }
        }
    }
}
