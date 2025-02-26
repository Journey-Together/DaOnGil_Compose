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
import kr.techit.lion.domain.exception.onError
import kr.techit.lion.domain.exception.onSuccess
import kr.techit.lion.domain.model.placereviewlist.PlaceReviewInfo
import kr.techit.lion.domain.repository.AuthRepository
import kr.techit.lion.domain.repository.PlaceRepository
import kr.techit.lion.presentation.compose.screen.intro.login.vm.model.LogInStatus
import kr.techit.lion.presentation.delegate.NetworkErrorDelegate
import kr.techit.lion.presentation.delegate.NetworkState
import javax.inject.Inject

@HiltViewModel
class ReviewListViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val getPlaceReviewListRepository: PlaceRepository
) : ViewModel() {

    @Inject
    lateinit var networkErrorDelegate: NetworkErrorDelegate
    val networkState: StateFlow<NetworkState> get() = networkErrorDelegate.networkState

    private val _loginState = MutableStateFlow<LogInStatus>(LogInStatus.Checking)
    val loginState = _loginState.asStateFlow()

    private val _placeReviewInfo = MutableLiveData<PlaceReviewInfo>()
    val placeReviewInfo : LiveData<PlaceReviewInfo> = _placeReviewInfo

    private val _isLastPage = MutableLiveData<Boolean>()
    val isLastPage : LiveData<Boolean> = _isLastPage

    companion object {
        const val PAGE_SIZE = 5
    }

    init {
        _isLastPage.value = false

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

    fun getPlaceReview(placeId: Long) = viewModelScope.launch {
        getPlaceReviewListRepository.getPlaceReviewList(placeId, PAGE_SIZE, 0).onSuccess {
            _placeReviewInfo.value = it
        }.onError {
            networkErrorDelegate.handleNetworkError(it)
        }
    }

    fun getPlaceReviewGuest(placeId: Long) = viewModelScope.launch {
        getPlaceReviewListRepository.getPlaceReviewListGuest(placeId, PAGE_SIZE, 0).onSuccess {
            _placeReviewInfo.value = it
        }.onError {
            networkErrorDelegate.handleNetworkError(it)
        }
    }

    fun getNewPlaceReview(placeId: Long) = viewModelScope.launch {
        val page = _placeReviewInfo.value?.pageNo

        if (page != null) {
            getPlaceReviewListRepository.getPlaceReviewList(placeId, size = PAGE_SIZE, page = page + 1).onSuccess {
                if (it.pageNo == it.totalPages) {
                    _isLastPage.value = true
                } else {
                    val reviews = _placeReviewInfo.value?.placeReviewList ?: emptyList()
                    val newReviews = reviews + it.placeReviewList
                    val newReviewData = it.copy(placeReviewList = newReviews)

                    _placeReviewInfo.value = newReviewData
                }
            }.onError {
                networkErrorDelegate.handleNetworkError(it)
            }
        }
    }

    fun getNewPlaceReviewGuest(placeId: Long) = viewModelScope.launch {
        val page = _placeReviewInfo.value?.pageNo

        if (page != null) {
            getPlaceReviewListRepository.getPlaceReviewListGuest(placeId, size = PAGE_SIZE, page = page + 1).onSuccess {
                if (it.pageNo == it.totalPages) {
                    _isLastPage.value = true
                } else {
                    val reviews = _placeReviewInfo.value?.placeReviewList ?: emptyList()
                    val newReviews = reviews + it.placeReviewList
                    val newReviewData = it.copy(placeReviewList = newReviews)

                    _placeReviewInfo.value = newReviewData
                }
            }.onError {
                networkErrorDelegate.handleNetworkError(it)
            }
        }
    }
}