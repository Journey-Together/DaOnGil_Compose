package kr.techit.lion.presentation.compose.screen.onboarding

import androidx.annotation.DrawableRes

data class OnBoardingPage(
    @DrawableRes val imageId: Int,
    val text1: String,
    val text2: String,
    val text3: String? = null
)
