package com.example.home.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.home.component.ImageScreen
import com.example.home.navigation.ParrotScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    //animation
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(targetValue = 0.9f, animationSpec = tween(
            durationMillis = 800,
            easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            }
        ))
        delay(2000L)
        navController.navigate(ParrotScreens.LoginScreen.name)
    })

    Surface(color = Color(0xFF1E2AE3)) {
        Surface(
            modifier = Modifier
                .scale(scale.value), color = Color(0xFF1E2AE3)
        ) {
            ImageScreen(0.dp)

        }
    }

}