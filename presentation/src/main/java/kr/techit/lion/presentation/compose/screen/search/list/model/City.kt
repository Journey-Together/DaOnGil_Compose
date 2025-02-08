package kr.techit.lion.presentation.compose.screen.search.list.model

sealed interface City{
    val names: List<String>

    data class AreaModel(
        override val names: List<String>,
    ) : City

    data class SigunguModel(
        override val names: List<String>
    ) : City
}