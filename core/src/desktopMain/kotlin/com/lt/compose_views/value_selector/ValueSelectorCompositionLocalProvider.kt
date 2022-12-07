package com.lt.compose_views.value_selector

import androidx.compose.runtime.Composable

@Composable
internal actual fun ValueSelectorCompositionLocalProvider(content: @Composable () -> Unit) {
    content()
}