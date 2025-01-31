package kr.techit.lion.presentation.compose.screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.navigation.IntroNavHost
import kr.techit.lion.presentation.compose.screen.onboarding.OnBoardingPage
import kr.techit.lion.presentation.main.MainActivity

@AndroidEntryPoint
class IntroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val destination = intent.getStringExtra("destination")

        val videoPath = "android.resource://" + packageName + "/" + R.raw.splash_video
        val videoUri = Uri.parse(videoPath)
        val pages = listOf(
            OnBoardingPage(
                R.drawable.onboarding_first,
                getString(R.string.text_onboarding_first_text1),
                getString(R.string.text_onboarding_first_text2),
            ),
            OnBoardingPage(
                R.drawable.onboarding_second,
                getString(R.string.text_onboarding_second_text1),
                getString(R.string.text_onboarding_second_text2),
            ),
            OnBoardingPage(
                R.drawable.onboarding_third,
                getString(R.string.text_onboarding_third_text1),
                getString(R.string.text_onboarding_third_text2),
            ),
            OnBoardingPage(
                R.drawable.onboarding_last,
                getString(R.string.text_onboarding_fourth_text1),
                getString(R.string.text_onboarding_fourth_text2),
                getString(R.string.text_onboarding_fourth_text3)
            )
        )

        setContent {
            IntroNavHost(
                videoUri = videoUri,
                onBoardingPage = pages,
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