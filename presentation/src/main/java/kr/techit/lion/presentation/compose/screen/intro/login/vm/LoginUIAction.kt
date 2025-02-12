package kr.techit.lion.presentation.compose.screen.intro.login.vm

import kr.techit.lion.presentation.compose.screen.intro.login.vm.model.LoginType

sealed interface LoginUIAction {
    data class OnCompleteKaKaoLogin(
        val loginType: String = LoginType.KAKAO.toString(),
        val accessToken: String,
        val refreshToken: String
    ) : LoginUIAction

    data class OnCompleteNaverLogin(
        val loginType: String = LoginType.NAVER.toString(),
        val accessToken: String,
        val refreshToken: String
    ) : LoginUIAction
}