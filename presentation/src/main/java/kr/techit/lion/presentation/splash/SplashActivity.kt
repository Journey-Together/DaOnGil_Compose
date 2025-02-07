package kr.techit.lion.presentation.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.databinding.ActivitySplashBinding
import kr.techit.lion.presentation.splash.vm.SplashViewModel
import androidx.core.net.toUri
import kotlinx.coroutines.delay
import kr.techit.lion.presentation.compose.screen.intro.IntroActivity
import kr.techit.lion.presentation.ext.repeatOnStarted
import kr.techit.lion.presentation.main.MainActivity
import kr.techit.lion.presentation.delegate.NetworkEvent
import kr.techit.lion.presentation.ext.showInfinitySnackBar
import kr.techit.lion.presentation.splash.vm.SplashUiEvent

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()

    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val videoPath = "android.resource://" + packageName + "/" + R.raw.splash_video
        with(binding.splashVideoView) {

            setVideoURI(videoPath.toUri())

            setOnPreparedListener { mp ->
                val videoWidth = mp.videoWidth.toFloat()
                val videoHeight = mp.videoHeight.toFloat()
                val videoAspectRatio = videoWidth / videoHeight

                val displayMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)
                val screenWidth = displayMetrics.widthPixels.toFloat()
                val screenHeight = displayMetrics.heightPixels.toFloat()

                val screenAspectRatio = screenWidth / screenHeight

                val layoutParams = this.layoutParams

                if (videoAspectRatio > screenAspectRatio) {
                    layoutParams.width = screenWidth.toInt()
                    layoutParams.height = (screenWidth / videoAspectRatio).toInt()
                } else {
                    layoutParams.width = screenWidth.toInt()
                    layoutParams.height = (screenWidth / videoAspectRatio).toInt()
                }
                this.layoutParams = layoutParams

                this.start()
            }

            repeatOnStarted {
                viewModel.networkEvent.collect { event ->
                    when (event) {
                        NetworkEvent.Loading -> Unit
                        NetworkEvent.Success -> {
                            startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
                            finish()
                        }
                        is NetworkEvent.Error -> {
                            showInfinitySnackBar(binding.root, event.msg)
                        }
                    }
                }
            }

            repeatOnStarted {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        SplashUiEvent.NavigateToMain -> {
                            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                            finish()
                        }
                        SplashUiEvent.NavigateToIntro -> {
                            startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
                            finish()
                        }
                        is SplashUiEvent.ShowSnackBar -> {
                            showInfinitySnackBar(binding.root, getString(event.message))
                        }
                    }
                }
            }
        }
    }
}