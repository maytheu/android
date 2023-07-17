package com.maytheu.quiz.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.maytheu.quiz.component.Questions

@Composable
fun QuizHome(quizViewModel: QuizViewModel = hiltViewModel()) {
    Questions(quizViewModel)
}

@Composable
fun BannerAdView() {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { ctx ->
            AdView(ctx).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = "ca-app-pub-7525032729846884/2891087020"
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}
