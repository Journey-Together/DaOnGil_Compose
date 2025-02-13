package kr.techit.lion.presentation.compose.screen.search.list.model

import kr.techit.lion.domain.model.search.Arrange
import kr.techit.lion.domain.model.search.ListSearchOption
import kr.techit.lion.presentation.compose.screen.search.model.CategoryStatus
import kr.techit.lion.presentation.compose.screen.search.model.Disability.PHYSICAL_DISABILITY
import kr.techit.lion.presentation.compose.screen.search.model.Disability.VISUAL_IMPAIRMENT
import kr.techit.lion.presentation.compose.screen.search.model.Disability.HEARING_IMPAIRMENT
import kr.techit.lion.presentation.compose.screen.search.model.Disability.ELDERLY_PEOPLE
import kr.techit.lion.presentation.compose.screen.search.model.Disability.INFANT_FAMILY
import java.util.TreeSet

data class ListOptionStatus(
    val categoryStatus: CategoryStatus,
    val page: Int,
    val detailFilter: TreeSet<Int>?,
    val areaCode: String?,
    val sigunguCode: String?,
    val arrange: String
){
    val disabilityType: TreeSet<Long>
        get() = TreeSet<Long>().apply {
            detailFilter?.let { filterList ->
                if (PHYSICAL_DISABILITY.optionCodes.any { it in filterList }) add(PHYSICAL_DISABILITY.code)
                if (VISUAL_IMPAIRMENT.optionCodes.any { it in filterList }) add(VISUAL_IMPAIRMENT.code)
                if (HEARING_IMPAIRMENT.optionCodes.any { it in filterList }) add(HEARING_IMPAIRMENT.code)
                if (INFANT_FAMILY.optionCodes.any { it in filterList }) add(INFANT_FAMILY.code)
                if (ELDERLY_PEOPLE.optionCodes.any { it in filterList }) add(ELDERLY_PEOPLE.code)
            }
        }

    fun toDomainModel(): ListSearchOption {
        return ListSearchOption(
            category = categoryStatus.name,
            page = page,
            size = 0,
            detailFilter = detailFilter?.map { it.toLong() } ?: emptyList(),
            areaCode = areaCode,
            sigunguCode = sigunguCode,
            query = null,
            arrange = arrange
        )
    }

    companion object {
        fun create(): ListOptionStatus {
            return ListOptionStatus(
                categoryStatus = CategoryStatus.PLACE,
                page = 0,
                detailFilter = TreeSet(),
                areaCode = null,
                sigunguCode = null,
                arrange = Arrange.SortByLatest.sortCode
            )
        }
    }
}