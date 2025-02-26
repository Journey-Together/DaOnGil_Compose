package kr.techit.lion.presentation.home.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.techit.lion.domain.model.detailplace.PlaceDetailInfo
import kr.techit.lion.domain.model.detailplace.PlaceDetailInfoGuest
import kr.techit.lion.domain.repository.AuthRepository
import kr.techit.lion.domain.repository.BookmarkRepository
import kr.techit.lion.domain.repository.PlaceRepository
import kr.techit.lion.presentation.delegate.NetworkErrorDelegate
import kr.techit.lion.domain.exception.onError
import kr.techit.lion.domain.exception.onSuccess
import kr.techit.lion.presentation.compose.screen.intro.login.vm.model.LogInStatus
import kr.techit.lion.presentation.delegate.NetworkState
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val getPlaceDetailInfoRepository: PlaceRepository,
    private val updateBookmarkRepository: BookmarkRepository
) : ViewModel() {

    @Inject
    lateinit var networkErrorDelegate: NetworkErrorDelegate
    val networkState: StateFlow<NetworkState> get() = networkErrorDelegate.networkState

    private val _loginState = MutableStateFlow<LogInStatus>(LogInStatus.Checking)
    val loginState = _loginState.asStateFlow()

    private val _detailPlaceInfo = MutableLiveData<PlaceDetailInfo>()
    val detailPlaceInfo : LiveData<PlaceDetailInfo> = _detailPlaceInfo

    private val _detailPlaceInfoGuest = MutableLiveData<PlaceDetailInfoGuest>()
    val detailPlaceInfoGuest : LiveData<PlaceDetailInfoGuest> = _detailPlaceInfoGuest

    private val _isBookmarkSuccess = MutableLiveData<Boolean>()
    val isBookmarkSuccess: LiveData<Boolean> = _isBookmarkSuccess

    private val _isBookmarkError = MutableLiveData<Boolean>()
    val isBookmarkError: LiveData<Boolean> = _isBookmarkError

    init {
        viewModelScope.launch {
            checkLoginState()
        }
    }

    private suspend fun checkLoginState(){
        authRepository.loggedIn.collect{ isLoggedIn ->
            if (isLoggedIn) _loginState.value = LogInStatus.LoggedIn
            else _loginState.value = LogInStatus.LoginRequired
        }
    }

    fun getDetailPlace(placeId: Long) = viewModelScope.launch {
        getPlaceDetailInfoRepository.getPlaceDetailInfo(placeId).onSuccess {
            _detailPlaceInfo.value = it
            networkErrorDelegate.handleNetworkSuccess()
        }.onError {
            networkErrorDelegate.handleNetworkError(it)
        }
    }

    fun getDetailPlaceGuest(placeId: Long) = viewModelScope.launch {
        getPlaceDetailInfoRepository.getPlaceDetailInfoGuest(placeId).onSuccess {
            _detailPlaceInfoGuest.value = it
            networkErrorDelegate.handleNetworkSuccess()
        }.onError {
            networkErrorDelegate.handleNetworkError(it)
        }
    }

    fun updateDetailPlaceBookmark(placeId: Long) = viewModelScope.launch {
        updateBookmarkRepository.updatePlaceBookmark(placeId).onSuccess {
            _isBookmarkSuccess.value = true
            _isBookmarkError.value = false
            networkErrorDelegate.handleNetworkSuccess()
        }.onError {
            _isBookmarkSuccess.value = false
            _isBookmarkError.value = true
            networkErrorDelegate.handleNetworkError(it)
        }
    }
}