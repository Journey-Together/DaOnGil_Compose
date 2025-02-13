package kr.techit.lion.presentation.compose.screen.intro.concern.vm

sealed interface ConcernUiEvent {
    data object NavigateToMain: ConcernUiEvent
}