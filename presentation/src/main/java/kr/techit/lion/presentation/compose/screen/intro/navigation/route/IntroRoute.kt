package kr.techit.lion.presentation.compose.screen.intro.navigation.route

sealed class IntroRoute(val route: String) {
    data object OnBoarding : IntroRoute("OnBoardingScreen")
    data object Login : IntroRoute("LoginScreen")
    data object Concern : IntroRoute("ConcernScreen")
}