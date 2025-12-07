package io.github.neronguyenvn.aiagentsnakegame.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SnakePart(modifier: Modifier = Modifier, color: Color) {
    Box(
        modifier = modifier
            .size(20.dp)
            .background(color, CircleShape)
    )
}

@Composable
fun Food(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(20.dp)
            .background(Color.Red, CircleShape)
    )
}
