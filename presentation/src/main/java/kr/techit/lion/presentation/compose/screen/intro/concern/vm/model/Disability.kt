package kr.techit.lion.presentation.compose.screen.intro.concern.vm.model

sealed interface Disability {
    data object PhysicalDisability : Disability
    data object VisualImpairment : Disability
    data object HearingImpairment : Disability
    data object InfantFamily : Disability
    data object ElderlyPeople : Disability
}