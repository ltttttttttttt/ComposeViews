package com.lt.compose_views.value_selector

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal actual fun ValueSelectorCompositionLocalProvider(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null,
        content = content
    )
}