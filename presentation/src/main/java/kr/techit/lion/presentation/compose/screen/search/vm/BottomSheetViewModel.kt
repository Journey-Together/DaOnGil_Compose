package kr.techit.lion.presentation.compose.screen.search.vm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kr.techit.lion.presentation.compose.screen.search.model.BottomSheetOptionState
import kr.techit.lion.presentation.compose.screen.search.model.Disability
import javax.inject.Inject

@HiltViewModel
class BottomSheetViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(BottomSheetOptionState.create())
    val uiState get() = _uiState.asStateFlow()

    fun addAllOption() {
        _uiState.update { it.addAllSpecificDisabilityOptions() }
    }

    fun modifyCurrentSelectedOption(optionCode: Int) {
        _uiState.update { it.updateCurrentSelectedOption(optionCode) }
    }

    fun modifySelectedOption() {
        _uiState.update { it.updateOption() }
    }

    fun onClickDisability(disability: Disability) {
        _uiState.update { it.updateDisability(disability) }
    }

    fun clearCurrentSelectedOption() {
        _uiState.update { it.clearCurrentSelectedOption() }
    }
}