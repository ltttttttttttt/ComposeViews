package com.lt.common_app

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lt.common_app.base.BaseComposeActivity
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.nav.PagerNav
import com.lt.compose_views.nav.PagerNavState
import com.lt.compose_views.text_field.GoodTextField

/**
 * creator: lt  2023/6/18  lt.dygzs@qq.com
 * effect :
 * warning:
 */
class NavA : BaseComposeActivity() {
    private val state = PagerNavState(
        listOf(
            "a" to { A() },
            "b" to { B() },
            "c" to { C() },
            "d" to { D() },
        )
    )

    @Composable
    override fun ComposeContent() {
        val currRoute by remember { state.createCurrRouteFlow() }.collectAsState(state.routeAndContents.first().first)
        Column {
            PagerNav(state, Modifier.fillMaxWidth().weight(1f))
            Row(Modifier.fillMaxWidth().height(45.dp)) {
                state.routeAndContents.forEach {
                    Button(
                        { state.nav(it.first) },
                        Modifier.weight(1f).fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = if (currRoute == it.first) Color.Blue else Color.Gray)
                    ) {
                        Text(it.first)
                    }
                }
            }
        }
    }

    @Composable
    private fun A() {
        LazyColumn(Modifier.fillMaxSize()) {
            items(100) {
                Text(it.toString())
            }
        }
    }

    @Composable
    private fun B() {
        ComposePager(5, Modifier.fillMaxSize()) {
            Text(index.toString())
        }
    }

    @Composable
    private fun C() {
        ComposePager(5, Modifier.fillMaxSize(), orientation = Orientation.Vertical) {
            Text(index.toString())
        }
    }

    @Composable
    private fun D() {
        GoodTextField("d", {})
    }
}