package kr.techit.lion.presentation.compose.screen.intro.login.vm.model

sealed interface LogInStatus {
    data object Checking : LogInStatus
    data object LoggedIn : LogInStatus
    data object LoginRequired : LogInStatus
}