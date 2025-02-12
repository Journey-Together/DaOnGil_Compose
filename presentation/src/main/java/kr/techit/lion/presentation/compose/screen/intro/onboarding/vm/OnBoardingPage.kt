package kr.techit.lion.presentation.compose.screen.intro.onboarding.vm

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingPage(
    @DrawableRes val imageId: Int,
    @StringRes val firstLine: Int,
    @StringRes val secondLine: Int,
    @StringRes val thirdLine: Int? = null
)