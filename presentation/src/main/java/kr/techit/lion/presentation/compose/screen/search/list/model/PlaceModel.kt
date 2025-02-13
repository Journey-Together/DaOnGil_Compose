package kr.techit.lion.presentation.compose.screen.search.list.model

import kr.techit.lion.domain.model.search.ListSearchResultList

data class PlaceModel(
    val placeName: String,
    val placeAddr: String,
    val placeId: Long,
    val placeImg: String = "",
    val disability: List<String> = emptyList(),
    val itemCount: Int
)

fun ListSearchResultList.toUiModel(): List<PlaceModel> = this.places.map {
    PlaceModel(
        placeName = it.place.name,
        placeAddr = it.place.address,
        placeId = it.place.placeId,
        placeImg = it.place.image,
        disability = it.place.disability,
        itemCount = itemSize
    )
}