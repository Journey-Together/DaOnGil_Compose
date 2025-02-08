package kr.techit.lion.presentation.compose.screen.search.map.model

import kr.techit.lion.domain.model.search.Arrange
import kr.techit.lion.domain.model.search.MapSearchOption
import kr.techit.lion.presentation.compose.screen.search.model.CategoryStatus
import kr.techit.lion.presentation.main.search.vm.model.DisabilityType
import java.util.TreeSet

data class MapOptionStatus(
    val categoryStatus: CategoryStatus,
    val location: Locate,
    val disabilityType: TreeSet<Long>?,
    val detailFilter: TreeSet<Long>?,
    val arrange: String
) {
    fun toDomainModel(): MapSearchOption {
        return MapSearchOption(
            category = categoryStatus.name,
            minX = location.minLongitude,
            maxX = location.maxLongitude,
            minY = location.minLatitude,
            maxY = location.maxLatitude,
            disabilityType = disabilityType?.toList() ?: emptyList(),
            detailFilter = detailFilter?.toList() ?: emptyList(),
            arrange = arrange
        )
    }

    companion object {
        fun create(): MapOptionStatus {
            return MapOptionStatus(
                categoryStatus = CategoryStatus.PLACE,
                location = Locate(
                    minLatitude = 0.0,
                    maxLatitude = 0.0,
                    minLongitude = 0.0,
                    maxLongitude = 0.0,
                ),
                disabilityType = DisabilityType.createDisabilityTypeCodes(),
                detailFilter = DisabilityType.createFilterCodes(),
                arrange = Arrange.SortByLatest.sortCode
            )
        }
    }
}

