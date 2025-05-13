package com.lt.common_app

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lt.common_app.base.BaseComposeActivity
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.ComposePagerScope
import com.lt.compose_views.nav.NavContent
import com.lt.compose_views.nav.PagerNav
import com.lt.compose_views.nav.PagerNavState
import com.lt.compose_views.other.ButtonWithNotRipple
import com.lt.compose_views.text_field.GoodTextField
import com.lt.compose_views.util.rememberMutableStateOf

/**
 * creator: lt  2023/6/18  lt.dygzs@qq.com
 * effect :
 * warning:
 */
class NavA : BaseComposeActivity() {
    private val state = PagerNavState(
        listOf(
            A(),
            B(),
            C(),
            D(),
        )
    )

    @Composable
    override fun ComposeContent() {
        val currRoute by remember { state.createCurrRouteFlow() }.collectAsState(state.navContents.first().route)
        Column {
            PagerNav(state, Modifier.fillMaxWidth().weight(1f))
            Row(Modifier.fillMaxWidth().height(45.dp)) {
                state.navContents.forEach {
                    Button(
                        { state.nav(it.route) },
                        Modifier.weight(1f).fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = if (currRoute == it.route) Color.Blue else Color.Gray)
                    ) {
                        Text(it.route)
                    }
                }
            }
        }
    }

    class A : NavContent {
        override val route: String = "a"

        @Composable
        override fun Content(scope: ComposePagerScope) {
            LazyColumn(Modifier.fillMaxSize()) {
                items(100) {
                    Text(it.toString())
                }
            }
        }
    }

    class B : NavContent {
        override val route: String = "b"

        @Composable
        override fun Content(scope: ComposePagerScope) {
            ComposePager(5, Modifier.fillMaxSize()) {
                Text(index.toString())
            }
        }
    }

    class C : NavContent {
        override val route: String = "c"

        @Composable
        override fun Content(scope: ComposePagerScope) {
            ComposePager(5, Modifier.fillMaxSize(), orientation = Orientation.Vertical) {
                Text(index.toString())
            }
        }
    }

    class D : NavContent {
        override val route: String = "d"

        @Composable
        override fun Content(scope: ComposePagerScope) {
            var text by rememberMutableStateOf { "d" }
            Column {
            GoodTextField(text, { text = it })
                ButtonWithNotRipple({
                    text = ""
                }) {
                    Text("clear")
                }
            }
        }
    }
}