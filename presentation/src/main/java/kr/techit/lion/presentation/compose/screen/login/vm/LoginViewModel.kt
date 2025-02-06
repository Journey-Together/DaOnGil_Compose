package kr.techit.lion.presentation.compose.screen.login.vm

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.techit.lion.domain.model.ConcernType
import kr.techit.lion.domain.model.hasAnyTrue
import kr.techit.lion.domain.repository.AuthRepository
import kr.techit.lion.domain.repository.MemberRepository
import kr.techit.lion.presentation.base.BaseViewModel
import kr.techit.lion.presentation.compose.screen.login.model.UserType
import kr.techit.lion.presentation.delegate.NetworkEventDelegate
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val memberRepository: MemberRepository,
    private val networkEventDelegate: NetworkEventDelegate
) : BaseViewModel() {

    val networkEvent get() = networkEventDelegate.event

    private val _userType = MutableStateFlow(UserType.Checking)
    val userType get() = _userType.asStateFlow()

    fun onCompleteLogIn(type: String, accessToken: String, refreshToken: String) {
        viewModelScope.launch(recordExceptionHandler) {
            authRepository.signIn(type, accessToken, refreshToken)
            checkUserState()
        }
    }

    private fun checkUserState() = execute(
        action = {
            memberRepository.getConcernType()
                 },
        eventHandler = networkEventDelegate,
        onSuccess = { type ->
            modifyUserState(type)
        }
    )

    private fun modifyUserState(type: ConcernType) {
        if (type.hasAnyTrue()) {
            _userType.value = UserType.ExistingUser
        } else {
            _userType.value = UserType.NewUser
        }
    }
}
