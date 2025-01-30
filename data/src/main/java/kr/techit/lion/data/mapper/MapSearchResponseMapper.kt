package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.search.MapSearchResult
import kr.techit.lion.domain.model.search.MapSearchResultList
import kr.techit.lion.network.response.search.map.MapSearchResponse

internal fun MapSearchResponse.toDomainModel(): MapSearchResultList {
    return MapSearchResultList(
        places = data.map {
            MapSearchResult(
                address = it.address,
                disability = it.disability,  // assuming disabilities are integers
                image = it.image,
                name = it.name,
                mapX = it.mapX,
                mapY = it.mapY,
                placeId = it.placeId
            )
        }
    )
}