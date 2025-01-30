package kr.techit.lion.presentation.compose.screen.login.model

sealed class LogInStatus {
    data object Checking : LogInStatus()
    data object LoggedIn : LogInStatus()
    data object LoginRequired : LogInStatus()
}