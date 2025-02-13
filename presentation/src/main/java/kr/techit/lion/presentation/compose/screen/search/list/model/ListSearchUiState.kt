package kr.techit.lion.presentation.compose.screen.search.list.model

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kr.techit.lion.domain.model.area.AreaCodeList
import kr.techit.lion.domain.model.area.SigunguCodeList
import kr.techit.lion.presentation.compose.screen.search.list.model.City.AreaModel
import kr.techit.lion.presentation.compose.screen.search.list.model.City.SigunguModel
import kr.techit.lion.presentation.compose.screen.search.list.model.PlaceModel
import kr.techit.lion.presentation.compose.screen.search.model.CategoryStatus
import java.util.TreeSet

data class ListSearchUiState(
    val place: List<PlaceModel>,
    val area: AreaModel,
    val sigungu: SigunguModel,
    var options: ListOptionStatus,
    val isLastPage: Boolean
) {
    val placeColumnSize: Int
        get() = (place.size + 1) / 2


    fun updatePlaceModel(newPlaces: List<PlaceModel>): ListSearchUiState {
        return this.copy(place = place + newPlaces)
    }

    fun updateCategoryStatus(categoryStatus: CategoryStatus): ListSearchUiState {
        return this.copy(
            place = emptyList(),
            options = options.copy(categoryStatus = categoryStatus)
        )
    }

    fun updateOptions(newOptions: TreeSet<Int>): ListSearchUiState {
        val currentFiler = options.detailFilter
        return copy(
            place = emptyList(),
            options = this.options.copy(
                detailFilter = currentFiler?.let { currentFilter ->
                    TreeSet(currentFilter).apply {
                        newOptions.forEach { option ->
                            if (contains(option)) {
                                remove(option)
                            } else {
                                add(option)
                            }
                        }
                    }
                } ?: TreeSet(newOptions)
            )
        )
    }

    fun updateSigunguOption(sigunguCode: String): ListSearchUiState {
        return this.copy(
            place = emptyList(),
            options = options.copy(sigunguCode = sigunguCode)
        )
    }

    fun updateAreaModel(areaCodeList: AreaCodeList): ListSearchUiState {
        return this.copy(
            place = emptyList(),
            area = area.copy(
                names = areaCodeList.areaList.map { it.name }.toPersistentList()
            )
        )
    }

    fun updateSigunguModel(
        areCode: String,
        sigunguCodeList: SigunguCodeList,
    ): ListSearchUiState {
        return this.copy(
            place = emptyList(),
            options = options.copy(areaCode = areCode),
            sigungu = sigungu.copy(
                names = sigunguCodeList.sigunguList.map { it.sigunguName }.toPersistentList()
            )
        )
    }

    companion object {
        fun create() = ListSearchUiState(
            place = emptyList(),
            area = AreaModel(persistentListOf()),
            sigungu = SigunguModel(persistentListOf()),
            options = ListOptionStatus.create(),
            isLastPage = false
        )
    }
}