package kr.techit.lion.presentation.splash.vm

import androidx.annotation.StringRes

sealed interface SplashUiEvent {
    data object NavigateToMain : SplashUiEvent
    data object NavigateToIntro : SplashUiEvent
    data class ShowSnackBar(@StringRes val message: Int) : SplashUiEvent
}