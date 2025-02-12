package kr.techit.lion.presentation.compose.screen.search.list.vm

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.techit.lion.domain.repository.AreaCodeRepository
import kr.techit.lion.domain.repository.PlaceRepository
import kr.techit.lion.domain.repository.SigunguCodeRepository
import kr.techit.lion.presentation.base.BaseViewModel
import kr.techit.lion.presentation.compose.screen.search.list.model.City
import kr.techit.lion.presentation.compose.screen.search.list.model.ListSearchUiState
import kr.techit.lion.presentation.compose.screen.search.model.CategoryStatus
import kr.techit.lion.presentation.compose.screen.search.model.Disability
import kr.techit.lion.presentation.delegate.NetworkEventDelegate
import java.util.TreeSet
import javax.inject.Inject

@HiltViewModel
class SearchListViewModel @Inject constructor(
    private val areaCodeRepository: AreaCodeRepository,
    private val sigunguCodeRepository: SigunguCodeRepository,
    private val placeRepository: PlaceRepository,
    private val networkEventDelegate: NetworkEventDelegate
) : BaseViewModel() {

    init {
        loadAreaModel()
    }

    val networkEvent get() = networkEventDelegate.event

    private val _uiState = MutableStateFlow(ListSearchUiState.create())
    val uiState get() = _uiState.asStateFlow()

    private val _uiEvent = Channel<ListSearchUiEvent>()
    val uiEvent: Flow<ListSearchUiEvent> get() = _uiEvent.receiveAsFlow()

    fun onListSearchAction(action: ListSearchUiAction) {
        when (action) {
            is ListSearchUiAction.OnSelectArea -> loadSigunguModel(action.areaName)
            is ListSearchUiAction.OnSelectSigungu -> onSelectSigungu(action.sigunguName)
            is ListSearchUiAction.OnClickDisabilityOptionButton -> onActionShowBottomSheet(action.disability)
        }
    }

    fun onActionShowBottomSheet(disability: Disability){
        viewModelScope.launch {
            _uiEvent.send(ListSearchUiEvent.ShowBottomSheet(disability))
        }
    }

    fun updateTabStatus(categoryStatus: CategoryStatus){
        _uiState.update { it.updateCategoryStatus(categoryStatus) }
    }

    fun updateBottomSheetOption(options: TreeSet<Int>) {
        _uiState.update { it.updateOptions(options) }
    }

    fun loadAreaModel() {
        viewModelScope.launch {
            val areaList = areaCodeRepository.getAllAreaCodes()
            _uiState.update { it.updateAreaModel(areaList) }
        }
    }

    fun loadSigunguModel(areaName: String) = triggerLoadPlaces {
        resetSigunguSelection()
        viewModelScope.launch {
            val areaCode = areaCodeRepository.getAreaCodeByName(areaName) ?: ""
            val sigunguList = sigunguCodeRepository.getAllSigunguCode(areaCode)
            _uiState.update { it.updateSigunguModel(areaCode, sigunguList) }
        }
    }

    fun onSelectSigungu(sigunguName: String) = triggerLoadPlaces {
        viewModelScope.launch {
            val currentAreaCode = _uiState.value.options.areaCode ?: ""
            val sigunguCode = sigunguCodeRepository.getSigunguCodeByVillageName(currentAreaCode, sigunguName) ?: ""
            _uiState.update { it.updateSigunguOption(sigunguCode) }
        }
    }

    fun loadPlaces() {
        execute(
            action = { placeRepository.getSearchPlaceResultByList(_uiState.value.options.toDomainModel()) },
            eventHandler = networkEventDelegate,
            onSuccess = { result ->

            }
        )
    }

    private fun triggerLoadPlaces(action: suspend () -> Unit) {
        viewModelScope.launch(recordExceptionHandler) {
            action()
            loadPlaces()
        }
    }

    fun resetSigunguSelection() {
        _uiState.update { it.copy(sigungu = City.SigunguModel(persistentListOf())) }
    }
}