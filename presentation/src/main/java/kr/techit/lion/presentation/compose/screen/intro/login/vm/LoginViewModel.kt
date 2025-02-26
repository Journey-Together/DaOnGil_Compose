package kr.techit.lion.presentation.compose.screen.intro.login.vm

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kr.techit.lion.domain.model.ConcernType
import kr.techit.lion.domain.model.hasAnyTrue
import kr.techit.lion.domain.repository.AuthRepository
import kr.techit.lion.domain.repository.MemberRepository
import kr.techit.lion.presentation.base.BaseViewModel
import kr.techit.lion.presentation.delegate.NetworkEventDelegate
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val memberRepository: MemberRepository,
    private val networkEventDelegate: NetworkEventDelegate
) : BaseViewModel() {

    val networkEvent get() = networkEventDelegate.event

    private val _uiEvent = Channel<LoginUiEvent>()
    val uiEvent: Flow<LoginUiEvent> = _uiEvent.receiveAsFlow()

    fun onLoginUiAction(action: LoginUIAction) {
        when (action) {
            is LoginUIAction.OnCompleteKaKaoLogin -> {
                onCompleteLogIn(action.loginType, action.accessToken, action.refreshToken)
            }

            is LoginUIAction.OnCompleteNaverLogin -> {
                onCompleteLogIn(action.loginType, action.accessToken, action.refreshToken)
            }
        }
    }

    fun onCompleteLogIn(type: String, accessToken: String, refreshToken: String) {
        viewModelScope.launch(recordExceptionHandler) {
            authRepository.signIn(type, accessToken, refreshToken)
            checkUserState()
        }
    }

    private fun checkUserState() = execute(
        action = { memberRepository.getConcernType() },
        eventHandler = networkEventDelegate,
        onSuccess = { type ->
            viewModelScope.launch {
                modifyUserState(type)
            }
        }
    )

    private suspend fun modifyUserState(type: ConcernType) {
        if (type.hasAnyTrue()) {
            //_uiEvent.send(LoginUiEvent.NavigateToMain)
            _uiEvent.send(LoginUiEvent.NavigateToConcern)
        } else {
            //_uiEvent.send(LoginUiEvent.NavigateToConcern)
        }
    }
}
