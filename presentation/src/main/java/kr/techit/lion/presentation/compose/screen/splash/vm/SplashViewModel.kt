package kr.techit.lion.presentation.compose.screen.splash.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.techit.lion.domain.repository.ActivationRepository
import kr.techit.lion.domain.usecase.areacode.InitAreaCodeInfoUseCase
import kr.techit.lion.domain.usecase.base.onError
import kr.techit.lion.domain.usecase.base.onSuccess
import kr.techit.lion.presentation.connectivity.connectivity.ConnectivityObserver
import kr.techit.lion.presentation.connectivity.connectivity.ConnectivityStatus
import kr.techit.lion.presentation.delegate.NetworkEvent
import kr.techit.lion.presentation.delegate.NetworkEventDelegate
import kr.techit.lion.presentation.ext.shareInUi
import kr.techit.lion.presentation.ext.stateInUi
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    activationRepository: ActivationRepository,
    private val initAreaCodeInfoUseCase: InitAreaCodeInfoUseCase,
    private val networkEventDelegate: NetworkEventDelegate,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    val networkEvent = networkEventDelegate.event

    val connectivityStatus = connectivityObserver.observe()
        .stateInUi(
            scope = viewModelScope,
            initialValue = ConnectivityStatus.Loading
        )

    val userActivationState = activationRepository
        .activation
        .shareInUi(scope = viewModelScope)

    suspend fun whenUserActivationIsDeActivate() {
        initAreaCodeInfoUseCase()
            .onSuccess {
                networkEventDelegate.event(viewModelScope, NetworkEvent.Success)
            }
            .onError { exception ->
                networkEventDelegate.submitThrowableEvent(viewModelScope, exception)
            }
    }
}