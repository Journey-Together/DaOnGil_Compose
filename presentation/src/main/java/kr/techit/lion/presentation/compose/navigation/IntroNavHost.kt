package kr.techit.lion.presentation.compose.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kr.techit.lion.presentation.compose.screen.login.LoginScreen
import kr.techit.lion.presentation.compose.navigation.route.IntroRoute
import kr.techit.lion.presentation.compose.screen.IntroActivity
import kr.techit.lion.presentation.compose.screen.concern.ConcernScreen
import kr.techit.lion.presentation.compose.screen.onboarding.OnboardingScreen
import kr.techit.lion.presentation.compose.screen.splash.SplashScreen
import kr.techit.lion.presentation.compose.screen.onboarding.OnBoardingPage
import kr.techit.lion.presentation.main.MainActivity

@Composable
fun IntroNavHost(
    videoUri: Uri,
    onBoardingPage: List<OnBoardingPage>,
    destination: String? = null,
    navigateToMain: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = destination ?: IntroRoute.Splash.route
    ){
        composable(route = IntroRoute.Splash.route) {
            SplashScreen(
                videoUri = videoUri,
                navigateToMain = {
                    navigateToMain()
                },
                navigateToOnBoarding = {
                    navController.navigate(IntroRoute.OnBoarding.route)
                    navController.popBackStack()
                }
            )
        }
        composable(route = IntroRoute.OnBoarding.route) {
            OnboardingScreen(
                onBoardingPage,
                navigateToLogin = { navController.navigate(IntroRoute.Login.route) },
                navigateToMain = { navigateToMain() }
            )
        }
        composable(route = IntroRoute.Login.route) {
            LoginScreen(
                navigateToMain = { navigateToMain() }
            )
        }

        composable(route = IntroRoute.Concern.route) {
            ConcernScreen(
                navigateToMain = { navigateToMain() }
            )
        }
    }
}