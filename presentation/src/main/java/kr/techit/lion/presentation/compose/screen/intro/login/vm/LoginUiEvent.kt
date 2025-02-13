package kr.techit.lion.presentation.compose.screen.intro.login.vm

sealed interface LoginUiEvent {
    data object NavigateToMain : LoginUiEvent
    data object NavigateToConcern : LoginUiEvent
}