package kr.techit.lion.presentation.compose.screen.intro.onboarding.vm

sealed interface OnBoardingEvent {
    data object NavigateToLogin : OnBoardingEvent
    data object NavigateToMain : OnBoardingEvent
}