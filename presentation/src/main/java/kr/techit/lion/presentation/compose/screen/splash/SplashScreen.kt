package kr.techit.lion.presentation.compose.screen.splash

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kr.techit.lion.presentation.compose.screen.splash.vm.SplashViewModel
import kr.techit.lion.presentation.connectivity.connectivity.ConnectivityStatus
import kr.techit.lion.presentation.delegate.NetworkEvent

@Composable
fun SplashScreen(
    videoUri: Uri,
    navigateToMain: () -> Unit,
    navigateToOnBoarding: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val delayForSplash = 2_700L
    val videoPlayingStatus = remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.networkEvent) {
        viewModel.networkEvent.collect { event ->
            when (event) {
                NetworkEvent.Loading -> Unit
                NetworkEvent.Success -> {
                    videoPlayingStatus.value = false
                    navigateToOnBoarding()
                }
                is NetworkEvent.Error -> {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(
                            message = event.msg,
                            duration = SnackbarDuration.Indefinite
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(viewModel.connectivityStatus) {
        viewModel.userActivationState.combine(viewModel.connectivityStatus)
        { isActivated, connectivityStatus ->
            when (isActivated) {
                true -> {
                    delay(delayForSplash)
                    videoPlayingStatus.value = false
                    navigateToMain()
                }
                false -> {
                    when (connectivityStatus) {
                        ConnectivityStatus.Loading -> Unit
                        ConnectivityStatus.Available -> viewModel.whenUserActivationIsDeActivate()
                        is ConnectivityStatus.OnLost -> {
                            coroutineScope.launch {
                                snackBarHostState.showSnackbar(
                                    message = context.getString(connectivityStatus.msg),
                                    duration = SnackbarDuration.Indefinite
                                )
                            }
                        }
                    }
                }
            }
        }.collect()
    }
    SplashScreen(videoUri, videoPlayingStatus.value, snackBarHostState)
}

@Composable
fun SplashScreen(
    videoUri: Uri,
    isPlaying: Boolean,
    snackBarHostState: SnackbarHostState
) {

    Box(modifier = Modifier
        .background(Color(0xFFFBFAF6))
        .fillMaxSize()
    ) {
        if (isPlaying) {
            AndroidView(
                factory = { context ->
                    VideoView(context).apply {
                        setVideoURI(videoUri)
                        this.start()
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}