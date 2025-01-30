package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.Place
import kr.techit.lion.domain.model.search.ListSearchResult
import kr.techit.lion.network.response.search.list.SearchPlaceResponse

internal fun SearchPlaceResponse.toDomainModel(): List<ListSearchResult> {
    return data.placeResList.map {
        ListSearchResult(
            Place(
                address = it.address,
                disability = it.disability,
                image = it.image,
                name = it.name,
                placeId = it.placeId
            )
        )
    }
}