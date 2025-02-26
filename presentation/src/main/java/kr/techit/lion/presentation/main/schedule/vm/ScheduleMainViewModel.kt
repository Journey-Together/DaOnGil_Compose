package kr.techit.lion.presentation.main.schedule.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.techit.lion.domain.exception.Result
import kr.techit.lion.domain.exception.onError
import kr.techit.lion.domain.exception.onSuccess
import kr.techit.lion.domain.model.MyMainSchedule
import kr.techit.lion.domain.model.OpenPlan
import kr.techit.lion.domain.model.OpenPlanInfo
import kr.techit.lion.domain.repository.AuthRepository
import kr.techit.lion.domain.repository.PlanRepository
import kr.techit.lion.presentation.compose.screen.intro.login.vm.model.LogInStatus
import kr.techit.lion.presentation.delegate.NetworkErrorDelegate
import javax.inject.Inject

@HiltViewModel
class ScheduleMainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val planRepository: PlanRepository
) : ViewModel() {

    @Inject
    lateinit var networkErrorDelegate: NetworkErrorDelegate

    private val _myMainPlanList = MutableLiveData<List<MyMainSchedule?>?>()
    val myMainPlanList: LiveData<List<MyMainSchedule?>?> = _myMainPlanList

    private val _openPlanList = MutableLiveData<List<OpenPlanInfo>>()
    val openPlanList: LiveData<List<OpenPlanInfo>> = _openPlanList

    private val _loginState = MutableStateFlow<LogInStatus>(LogInStatus.Checking)
    val loginState = _loginState.asStateFlow()

    val networkState get() = networkErrorDelegate.networkState

    init {
        viewModelScope.launch {
            checkLoginState()
        }
    }

    fun getScheduleMainLists() =
        viewModelScope.launch {

            val myMainPlanDeferred = async { planRepository.getMyMainSchedule() }
            val openPlanDeferred = async { planRepository.getOpenPlanList(8, 0) }

            val myMainPlanResult = myMainPlanDeferred.await()
            val openPlanResult = openPlanDeferred.await()

            handleResults(myMainPlanResult, openPlanResult)


        }

    private fun handleResults(
        myMainPlanResult: Result<List<MyMainSchedule?>?>,
        openPlanResult: Result<OpenPlan>
    ) {

        val allSuccess = myMainPlanResult is Result.Success && openPlanResult is Result.Success
        if (allSuccess) {
            myMainPlanResult.onSuccess {
                _myMainPlanList.value = it
            }
            openPlanResult.onSuccess {
                _openPlanList.value = it.openPlanList
            }

            networkErrorDelegate.handleNetworkSuccess()
        } else {

            if (myMainPlanResult is Result.Error) {
                networkErrorDelegate.handleNetworkError(myMainPlanResult.error)
            }
            if (openPlanResult is Result.Error) {
                networkErrorDelegate.handleNetworkError(openPlanResult.error)
            }
        }
    }

    fun getOpenPlanList() =
        viewModelScope.launch {
            planRepository.getOpenPlanList(8, 0).onSuccess {
                _openPlanList.value = it.openPlanList
                networkErrorDelegate.handleNetworkSuccess()
            }.onError {
                networkErrorDelegate.handleNetworkError(it)
            }
        }

    private fun checkLoginState() =
        viewModelScope.launch {
            authRepository.loggedIn.collect { isLoggedIn ->
                if (isLoggedIn) _loginState.value = LogInStatus.LoggedIn
                else _loginState.value = LogInStatus.LoginRequired
            }
        }
}