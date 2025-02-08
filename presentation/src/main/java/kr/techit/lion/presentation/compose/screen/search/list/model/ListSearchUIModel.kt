package kr.techit.lion.presentation.compose.screen.search.list.model

import kr.techit.lion.domain.model.search.ListSearchResultList
import kr.techit.lion.presentation.main.search.vm.model.DisabilityType

sealed class ListSearchUIModel() {
    data class CategoryModel(
        val optionState: MutableMap<DisabilityType, Int>
    ) : ListSearchUIModel()

    data class SortModel(
        val totalItemCount: Int,
    ) : ListSearchUIModel()

    data class NoPlaceModel(
        val msg: String = "검색 결과가 없어요\n다시 검색 해주세요"
    ) : ListSearchUIModel()

    data class PlaceModel(
        val placeName: String,
        val placeAddr: String,
        val placeId: Long,
        val placeImg: String = "",
        val disability: List<String> = emptyList(),
        val itemCount: Int
    ) : ListSearchUIModel()
}

fun ListSearchResultList.toUiModel(): List<ListSearchUIModel.PlaceModel> =
    this.places.map {
        ListSearchUIModel.PlaceModel(
            placeName = it.place.name,
            placeAddr = it.place.address,
            placeId = it.place.placeId,
            placeImg = it.place.image,
            disability = it.place.disability,
            itemCount = itemSize
        )
    }