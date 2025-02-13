package kr.techit.lion.presentation.compose.screen.search.list.vm

import kr.techit.lion.presentation.compose.screen.search.model.Disability

sealed interface ListSearchUiEvent {
    data class ShowBottomSheet(val disability: Disability) : ListSearchUiEvent
}