package kr.techit.lion.presentation.compose.screen.intro.concern.vm

import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf
import kr.techit.lion.presentation.compose.screen.intro.concern.vm.model.Disability

data class ConcernUiState (
    val selectedConcernType: PersistentSet<Disability> = persistentSetOf()
)