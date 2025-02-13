package kr.techit.lion.presentation.main.home.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kr.techit.lion.domain.exception.onError
import kr.techit.lion.domain.exception.onSuccess
import kr.techit.lion.domain.model.mainplace.AroundPlace
import kr.techit.lion.domain.model.mainplace.RecommendPlace
import kr.techit.lion.domain.repository.ActivationRepository
import kr.techit.lion.domain.repository.AreaCodeRepository
import kr.techit.lion.domain.repository.NaverMapRepository
import kr.techit.lion.domain.repository.PlaceRepository
import kr.techit.lion.domain.repository.SigunguCodeRepository
import kr.techit.lion.presentation.delegate.NetworkErrorDelegate
import kr.techit.lion.presentation.delegate.NetworkState
import kr.techit.lion.presentation.main.home.HomeMainFragment.Companion.DEFAULT_AREA
import kr.techit.lion.presentation.main.home.HomeMainFragment.Companion.DEFAULT_SIGUNGU
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val areaCodeRepository: AreaCodeRepository,
    private val sigunguCodeRepository: SigunguCodeRepository,
    private val activationRepository: ActivationRepository,
    private val naverMapRepository: NaverMapRepository
) : ViewModel() {

    init {
        checkUserActivation()
    }

    @Inject
    lateinit var networkErrorDelegate: NetworkErrorDelegate
    val networkState: StateFlow<NetworkState> get() = networkErrorDelegate.networkState

    private val _aroundPlaceInfo = MutableLiveData<List<AroundPlace>>()
    val aroundPlaceInfo: LiveData<List<AroundPlace>> = _aroundPlaceInfo

    private val _recommendPlaceInfo = MutableLiveData<List<RecommendPlace>>()
    val recommendPlaceInfo: LiveData<List<RecommendPlace>> = _recommendPlaceInfo

    private val _userActivationState = MutableSharedFlow<Boolean>(replay = 1)
    val userActivationState: SharedFlow<Boolean> = _userActivationState.asSharedFlow()

    private val _area = MutableLiveData<String>()
    val area: LiveData<String> = _area

    private val _locationMessage = MutableLiveData<String>()
    val locationMessage: LiveData<String> get() = _locationMessage

    fun checkUserActivation(){
        viewModelScope.launch {
            val activation = activationRepository.checkUserActivation()
            _userActivationState.emit(activation)
        }
    }

    fun setActivation() {
        viewModelScope.launch {
            activationRepository.saveUserActivation()
        }
    }

    fun getPlaceMain(area: String, sigungu: String) = viewModelScope.launch(Dispatchers.IO) {

        var areaCode = areaCodeRepository.getAreaCodeByName(area)
        var sigunguCode = areaCode?.let {
            sigunguCodeRepository.getSigunguCodeByVillageName(sigungu, it)
        }

        if (areaCode == null || sigunguCode == null) {
            _locationMessage.postValue("위치를 찾을 수 없어 기본값($DEFAULT_AREA, $DEFAULT_SIGUNGU)으로 설정합니다.")
            areaCode = areaCodeRepository.getAreaCodeByName(DEFAULT_AREA)
            sigunguCode = areaCode?.let { sigunguCodeRepository.getSigunguCodeByVillageName(DEFAULT_SIGUNGU, it) }
        }

        if (areaCode != null && sigunguCode != null) {
            placeRepository.getPlaceMainInfo(areaCode, sigunguCode).onSuccess {
                _aroundPlaceInfo.postValue(it.aroundPlaceList)
                _recommendPlaceInfo.postValue(it.recommendPlaceList)

                networkErrorDelegate.handleNetworkSuccess()
            }.onError {
                networkErrorDelegate.handleNetworkError(it)
            }
        }
    }

    private fun getAreaCode(area: String) {
        viewModelScope.launch {
            areaCodeRepository.getAreaCodeByName(area)
        }
    }


    fun getUserLocationRegion(coords: String) = viewModelScope.launch {
        naverMapRepository.getReverseGeoCode(coords).onSuccess {
            if (it.code == 0) {
                if (it.results[0].areaDetail.isNullOrEmpty()) {
                    _area.value = it.results[0].area.toString()
                } else {
                    _area.value = "${it.results[0].area} ${it.results[0].areaDetail}"
                }
            } else {
                _area.value = "결과없음"
            }
        }.onError {
            networkErrorDelegate.handleNetworkError(it)
        }
    }
}