package com.maytheu.reader.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.maytheu.reader.components.ReaderLogo
import com.maytheu.reader.navigation.ReaderScreens
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController = NavController(context = LocalContext.current)) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    //show animation
    LaunchedEffect(key1 = true) {
        scale.animateTo(targetValue = 0.9f, animationSpec = tween(
            durationMillis = 900, easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            }
        ))
        delay(1500L)
        //check if user
        navController.navigate(ReaderScreens.LoginScreen.name)
    }

    Surface(
        modifier = Modifier
            .padding(20.dp)
            .size(400.dp)
            .scale(scale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(1.dp), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ReaderLogo()

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "\"Insight, Develop and Grow\"",
                style = MaterialTheme.typography.h5,
                color = Color.LightGray
            )
        }
    }
}

//@Composable
//fun ReaderLogo(modifier: Modifier = Modifier) {
//    Text(
//        text = "ReAdEr",
//        modifier = modifier.padding(bottom = 10.dp),
//        style = MaterialTheme.typography.h3,
//        color = Color.Red.copy(alpha = 0.5f)
//    )
//}