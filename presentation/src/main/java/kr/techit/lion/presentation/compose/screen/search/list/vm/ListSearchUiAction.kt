package kr.techit.lion.presentation.compose.screen.search.list.vm

import kr.techit.lion.presentation.compose.screen.search.model.Disability

sealed interface ListSearchUiAction {
    data class OnSelectArea(val areaName: String) : ListSearchUiAction
    data class OnSelectSigungu(val sigunguName: String) : ListSearchUiAction
    data class OnClickDisabilityOptionButton(val disability: Disability) : ListSearchUiAction
}