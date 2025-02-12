package kr.techit.lion.presentation.compose.screen.intro.concern.vm

import kr.techit.lion.presentation.compose.screen.intro.concern.vm.model.Disability

sealed interface ConcernUiAction {
    data class OnClickConcernButton(
        val type: Disability
    ): ConcernUiAction
    data object OnClickSubmitButton: ConcernUiAction
}