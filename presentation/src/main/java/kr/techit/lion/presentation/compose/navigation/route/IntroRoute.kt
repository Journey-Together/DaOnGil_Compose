package kr.techit.lion.presentation.compose.navigation.route

sealed class IntroRoute(val route: String) {
    data object Splash : IntroRoute("SplashScreen")
    data object OnBoarding : IntroRoute("OnBoardingScreen")
    data object Login : IntroRoute("LoginScreen")
    data object Concern : IntroRoute("ConcernScreen")
}