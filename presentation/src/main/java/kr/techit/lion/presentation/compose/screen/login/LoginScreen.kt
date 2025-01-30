package kr.techit.lion.presentation.compose.screen.login

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.component.LoginButton
import kr.techit.lion.presentation.compose.screen.login.model.LoginType
import kr.techit.lion.presentation.compose.screen.login.vm.LoginViewModel

@Composable
fun LoginScreen(
    navigateToMain: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    LoginScreen(
        navigateToMain = navigateToMain,
        onCompleteLogin = { loginType, accessToken, refreshToken ->
            viewModel.onCompleteLogIn(loginType, accessToken, refreshToken)
        }
    )
}

@Composable
fun LoginScreen(
    navigateToMain: () -> Unit,
    onCompleteLogin: (loginType: String, accessToken: String, refreshToken: String) -> Unit,
){
    val context = LocalContext.current
    val KakaoYellow = Color(0xFFFEE500)
    val gray10 = Color(0xFF141414)
    val NaverGreen = Color(0xFF03C76A)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = stringResource(R.string.text_hello_plz_login)
        )

        Spacer(modifier = Modifier.weight(0.1f))

        LoginButton(
            action = {
                val kakao = LoginType.KAKAO.toString()

                val callback: (OAuthToken?, Throwable?) -> Unit = { token, _ ->
                    if (token != null) {
                        onCompleteLogin(kakao, token.accessToken, token.refreshToken)
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
                            onCompleteLogin(kakao, token.accessToken, token.refreshToken)
                        }
                    }
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                }
            },
            resourceId = R.drawable.kakao_login
        )

        LoginButton(
            action = {
                NaverIdLoginSDK.authenticate(context, object : OAuthLoginCallback {
                    override fun onSuccess() {
                        //binding.progressbar.visibility = View.GONE

                        val naver = LoginType.NAVER.toString()
                        val accessToken = NaverIdLoginSDK.getAccessToken()
                        val refreshToken = NaverIdLoginSDK.getRefreshToken()

                        if (accessToken != null && refreshToken != null) {
                            onCompleteLogin(naver, accessToken, refreshToken)
                        }
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                        // 로그인 실패
                        //binding.progressbar.visibility = View.GONE
                        val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                        val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                        Log.d("NaverLoginFailed", "$errorCode : $errorDescription")
                    }

                    override fun onError(errorCode: Int, message: String) {
                        // 로그인 중 오류 발생
                        //binding.progressbar.visibility = View.GONE
                        onFailure(errorCode, message)
                    }
                })
            },
            resourceId = R.drawable.naver_login
        )
    }
}
@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginScreen(
        navigateToMain = {},
        onCompleteLogin = { loginType, accessToken, refreshToken -> }
    )
}