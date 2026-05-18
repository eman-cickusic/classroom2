package com.classroom2.app.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.asPaddingValues

/**
 * Top-level container for screens. Lays a surface background, applies system-bar
 * insets, lets a header slot render above scrolling content.
 */
@Composable
fun ClassroomScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable (PaddingValues) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues())
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                topBar()
                Box(modifier = Modifier.weight(1f, fill = true)) {
                    content(contentPadding)
                }
                bottomBar()
            }
        }
    }
}
