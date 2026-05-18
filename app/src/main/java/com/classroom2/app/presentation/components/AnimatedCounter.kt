package com.classroom2.app.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun AnimatedCounter(
    value: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineLarge,
    color: Color = LocalContentColor.current
) {
    AnimatedContent(
        targetState = value,
        transitionSpec = {
            if (targetState > initialState) {
                (slideInVertically(animationSpec = tween(220)) { it } + fadeIn(tween(220))) togetherWith
                    (slideOutVertically(animationSpec = tween(220)) { -it } + fadeOut(tween(220)))
            } else {
                (slideInVertically(animationSpec = tween(220)) { -it } + fadeIn(tween(220))) togetherWith
                    (slideOutVertically(animationSpec = tween(220)) { it } + fadeOut(tween(220)))
            }.using(androidx.compose.animation.SizeTransform(clip = false))
        },
        modifier = modifier,
        label = "counter"
    ) { target ->
        Text(text = target.toString(), style = style, color = color)
    }
}
