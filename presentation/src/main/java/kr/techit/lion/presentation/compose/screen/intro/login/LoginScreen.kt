package kr.techit.lion.presentation.compose.screen.intro.login

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import kotlinx.coroutines.launch
import kr.techit.lion.designsystem.component.ProgressBarScreen
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.common.ObserveAsEvents
import kr.techit.lion.presentation.compose.screen.intro.login.component.LoginButton
import kr.techit.lion.presentation.compose.screen.intro.login.vm.LoginUIAction
import kr.techit.lion.presentation.compose.screen.intro.login.vm.LoginUiEvent
import kr.techit.lion.presentation.compose.screen.intro.login.vm.LoginViewModel
import kr.techit.lion.presentation.delegate.NetworkEvent

@Composable
fun LoginScreen(
    navigateToMain: () -> Unit,
    navigateToConcern: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showProgressBar by remember { mutableStateOf(false) }

    ObserveAsEvents(viewModel.networkEvent) { event ->
        when (event) {
            is NetworkEvent.Loading -> {
                showProgressBar = true
            }

            is NetworkEvent.Success -> {
                showProgressBar = false
            }

            is NetworkEvent.Error -> {
                showProgressBar = false
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        message = event.msg,
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            LoginUiEvent.NavigateToMain -> navigateToMain()
            LoginUiEvent.NavigateToConcern -> navigateToConcern()
        }
    }

    LoginScreen(
        showProgressBar = showProgressBar,
        snackBarHost = snackBarHostState,
        loginAction = viewModel::onLoginUiAction
    )
}

@Composable
fun LoginScreen(
    showProgressBar: Boolean,
    snackBarHost: SnackbarHostState,
    loginAction: (LoginUIAction) -> Unit,
) {
    val context = LocalContext.current

    ProgressBarScreen(
        showProgressBar = showProgressBar,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
            ) {
                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = stringResource(R.string.text_hello_plz_login)
                )

                Spacer(modifier = Modifier.weight(0.5f))

                LoginButton(
                    action = {
                        val callback: (OAuthToken?, Throwable?) -> Unit = { token, _ ->
                            if (token != null) {
                                loginAction(
                                    LoginUIAction.OnCompleteKaKaoLogin(
                                        accessToken = token.accessToken,
                                        refreshToken = token.refreshToken
                                    )
                                )
                            }
                        }
                        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
                        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                                if (error != null) {
                                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                        return@loginWithKakaoTalk
                                    }

                                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                                    UserApiClient.instance.loginWithKakaoAccount(
                                        context,
                                        callback = callback
                                    )
                                } else if (token != null) {
                                    loginAction(
                                        LoginUIAction.OnCompleteKaKaoLogin(
                                            accessToken = token.accessToken,
                                            refreshToken = token.refreshToken
                                        )
                                    )
                                }
                            }
                        } else {
                            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                        }
                    },
                    resourceId = R.drawable.kakao_login
                )

                SnackbarHost(hostState = snackBarHost)

                LoginButton(
                    action = {
                        NaverIdLoginSDK.authenticate(context, object : OAuthLoginCallback {
                            override fun onSuccess() {
                                val accessToken = NaverIdLoginSDK.getAccessToken()
                                val refreshToken = NaverIdLoginSDK.getRefreshToken()

                                if (accessToken != null && refreshToken != null) {
                                    loginAction(
                                        LoginUIAction.OnCompleteNaverLogin(
                                            accessToken = accessToken,
                                            refreshToken = refreshToken
                                        )
                                    )
                                }
                            }

                            override fun onFailure(httpStatus: Int, message: String) {
                                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                                Log.d("NaverLoginFailed", "$errorCode : $errorDescription")
                            }

                            override fun onError(errorCode: Int, message: String) {
                                // 로그인 중 오류 발생
                                onFailure(errorCode, message)
                            }
                        })
                    },
                    resourceId = R.drawable.naver_login
                )
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginScreen(
        showProgressBar = true,
        snackBarHost = SnackbarHostState(),
        loginAction = {},
    )
}