package kr.techit.lion.presentation.compose.screen.intro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kr.techit.lion.presentation.compose.screen.intro.navigation.route.IntroRoute
import kr.techit.lion.presentation.compose.screen.intro.concern.ConcernScreen
import kr.techit.lion.presentation.compose.screen.intro.login.LoginScreen
import kr.techit.lion.presentation.compose.screen.intro.login.vm.LoginUiEvent
import kr.techit.lion.presentation.compose.screen.intro.onboarding.OnboardingScreen
import kr.techit.lion.presentation.compose.screen.intro.onboarding.vm.OnBoardingEvent

@Composable
fun IntroNavHost(
    navigateToMain: () -> Unit,
    destination: String? = null
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = destination ?: IntroRoute.OnBoarding.route
    ) {
        composable(route = IntroRoute.OnBoarding.route) {
            OnboardingScreen(
                navigateToMain = { navigateToMain() },
                navigateToLogin = { navController.navigate(IntroRoute.Login.route) }
            )
        }

        composable(route = IntroRoute.Login.route) {
            LoginScreen(
                navigateToMain = { navigateToMain() },
                navigateToConcern = {
                    navController.navigate(IntroRoute.Concern.route) {
                        popUpTo(IntroRoute.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = IntroRoute.Concern.route) {
            ConcernScreen(
                navigateToMain = { navigateToMain() }
            )
        }
    }
}