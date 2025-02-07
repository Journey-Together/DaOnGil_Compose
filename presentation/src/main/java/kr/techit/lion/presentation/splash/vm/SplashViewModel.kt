package kr.techit.lion.presentation.splash.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
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

    val userActivationState = activationRepository
        .activation
        .shareInUi(scope = viewModelScope)

    fun initializedUserActivation() {
        viewModelScope.launch {
            combine(
                userActivationState,
                connectivityStatus
            ) { isActivated, connectivityStatus ->
                when (isActivated) {
                    true -> {
                        delay(2700L)
                        _uiEvent.send(SplashUiEvent.NavigateToMain)
                    }

                    false -> {
                        when (connectivityStatus) {
                            ConnectivityStatus.Loading -> Unit
                            ConnectivityStatus.Available -> whenUserActivationIsDeActivate()
                            is ConnectivityStatus.OnLost -> {
                                _uiEvent.send(SplashUiEvent.ShowSnackBar(connectivityStatus.msg))
                            }
                        }
                    }
                }
            }.collect()
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