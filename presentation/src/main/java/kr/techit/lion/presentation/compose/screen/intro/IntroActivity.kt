package kr.techit.lion.presentation.compose.screen.intro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.net.toUri
import dagger.hilt.android.AndroidEntryPoint
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.screen.intro.navigation.IntroNavHost
import kr.techit.lion.presentation.compose.screen.intro.onboarding.vm.OnBoardingPage
import kr.techit.lion.presentation.compose.screen.search.SearchPlaceMainScreen
import kr.techit.lion.presentation.main.MainActivity

@AndroidEntryPoint
class IntroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val destination = intent.getStringExtra("destination")

        setContent {
            IntroNavHost(
                destination = destination,
                navigateToMain = {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}