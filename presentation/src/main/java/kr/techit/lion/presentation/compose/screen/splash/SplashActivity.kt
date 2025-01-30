package kr.techit.lion.presentation.compose.screen.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.navigation.route.IntroRoute
import kr.techit.lion.presentation.compose.screen.IntroActivity
import kr.techit.lion.presentation.main.MainActivity

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.splash_video
        val videoUri = Uri.parse(videoPath)

        setContent {
            SplashScreen(
                videoUri = videoUri,
                navigateToMain = {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                },
                navigateToOnBoarding = {
                    val intent = Intent(this, IntroActivity::class.java).apply {
                        putExtra("destination", IntroRoute.OnBoarding.route)
                    }
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}