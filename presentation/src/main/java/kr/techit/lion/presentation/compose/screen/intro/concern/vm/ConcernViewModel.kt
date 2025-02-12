package kr.techit.lion.presentation.compose.screen.intro.concern.vm

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.techit.lion.domain.model.ConcernType
import kr.techit.lion.domain.repository.MemberRepository
import kr.techit.lion.presentation.base.BaseViewModel
import kr.techit.lion.presentation.compose.screen.intro.concern.vm.model.Disability
import kr.techit.lion.presentation.delegate.NetworkEventDelegate
import javax.inject.Inject

@HiltViewModel
class ConcernViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val networkEventDelegate: NetworkEventDelegate
): BaseViewModel() {

    val networkEvent get() = networkEventDelegate.event

    private val _uiState = MutableStateFlow<ConcernUiState>(ConcernUiState())
    val uiState: StateFlow<ConcernUiState> get() = _uiState.asStateFlow()

    private val _uiEvent = Channel<ConcernUiEvent>()
    val uiEvent: Flow<ConcernUiEvent> get() = _uiEvent.receiveAsFlow()

    fun onConcernUiAction(action: ConcernUiAction) {
        when (action) {
            is ConcernUiAction.OnClickConcernButton -> onSelectConcern(action.type)
            is ConcernUiAction.OnClickSubmitButton -> onClickSubmitButton()
        }
    }

    fun onSelectConcern(type: Disability) {
        _uiState.update { state ->
            val updatedList = if (state.selectedConcernType.contains(type)) {
                state.selectedConcernType.remove(type)
            } else {
                state.selectedConcernType.add(type)
            }
            state.copy(selectedConcernType = updatedList)
        }
    }

    fun onClickSubmitButton(){
        val currentValues = _uiState.value.selectedConcernType
        val model = ConcernType(
            isPhysical = currentValues.contains(Disability.PhysicalDisability),
            isHear = currentValues.contains(Disability.HearingImpairment),
            isVisual = currentValues.contains(Disability.VisualImpairment),
            isElderly = currentValues.contains(Disability.ElderlyPeople),
            isChild = currentValues.contains(Disability.InfantFamily)
        )
        execute(
            action = { memberRepository.updateConcernType(model) },
            eventHandler = networkEventDelegate,
            onSuccess = {
                viewModelScope.launch {
                    _uiEvent.send(ConcernUiEvent.NavigateToMain)
                }
            }
        )
    }
}