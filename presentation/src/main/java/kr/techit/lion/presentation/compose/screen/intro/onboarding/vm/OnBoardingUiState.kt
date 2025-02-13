package kr.techit.lion.presentation.compose.screen.intro.onboarding.vm

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kr.techit.lion.presentation.R

data class OnBoardingUiState(
    val onBoardingPages : ImmutableList<OnBoardingPage> = persistentListOf<OnBoardingPage>(
        OnBoardingPage(
            R.drawable.onboarding_first,
            R.string.text_onboarding_first_text1,
            R.string.text_onboarding_first_text2,
        ),
        OnBoardingPage(
            R.drawable.onboarding_second,
            R.string.text_onboarding_second_text1,
            R.string.text_onboarding_second_text2,
        ),
        OnBoardingPage(
            R.drawable.onboarding_third,
            R.string.text_onboarding_third_text1,
            R.string.text_onboarding_third_text2,
        ),
        OnBoardingPage(
            R.drawable.onboarding_last,
            R.string.text_onboarding_fourth_text1,
            R.string.text_onboarding_fourth_text2,
            R.string.text_onboarding_fourth_text3
        )
    )
)