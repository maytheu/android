package com.maytheu.reader.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//logo composable
@Composable
fun ReaderLogo(modifier: Modifier = Modifier) {
    Text(
        text = "ReAdEr",
        modifier = modifier.padding(bottom = 10.dp),
        style = MaterialTheme.typography.h3,
        color = Color.Red.copy(alpha = 0.5f)
    )
}
