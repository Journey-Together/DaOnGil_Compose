package kr.techit.lion.presentation.splash.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kr.techit.lion.domain.repository.ActivationRepository
import kr.techit.lion.domain.usecase.areacode.InitAreaCodeInfoUseCase
import kr.techit.lion.domain.usecase.base.onError
import kr.techit.lion.domain.usecase.base.onSuccess
import kr.techit.lion.presentation.connectivity.connectivity.ConnectivityObserver
import kr.techit.lion.presentation.connectivity.connectivity.ConnectivityStatus
import kr.techit.lion.presentation.delegate.NetworkEvent
import kr.techit.lion.presentation.delegate.NetworkEventDelegate
import kr.techit.lion.presentation.ext.stateInUi
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val activationRepository: ActivationRepository,
    private val initAreaCodeInfoUseCase: InitAreaCodeInfoUseCase,
    private val networkEventDelegate: NetworkEventDelegate,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    init {
        initializedUserActivation()
    }

    private val _uiEvent = Channel<SplashUiEvent>()
    val uiEvent: Flow<SplashUiEvent> = _uiEvent.receiveAsFlow()

    val networkEvent = networkEventDelegate.event

    val connectivityStatus = connectivityObserver.observe()
        .stateInUi(
            scope = viewModelScope,
            initialValue = ConnectivityStatus.Loading
        )

    fun initializedUserActivation() {
        viewModelScope.launch {
            val isActivated = activationRepository.checkUserActivation()
            when(isActivated) {
                true -> {
                    delay(2500L)
                    _uiEvent.send(SplashUiEvent.NavigateToMain)
                }
                false -> {
                     whenUserActivationIsDeActivate()
                }
            }
        }
    }

    suspend fun whenUserActivationIsDeActivate() {
        initAreaCodeInfoUseCase()
            .onSuccess {
                networkEventDelegate.event(viewModelScope, NetworkEvent.Success)
                viewModelScope.launch {
                    _uiEvent.send(SplashUiEvent.NavigateToIntro)
                }
            }
            .onError { exception ->
                networkEventDelegate.submitThrowableEvent(viewModelScope, exception)
            }
    }
}