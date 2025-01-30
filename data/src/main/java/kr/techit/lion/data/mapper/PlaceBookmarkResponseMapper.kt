package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.PlaceBookmark
import kr.techit.lion.network.response.bookmark.PlaceBookmarkResponse

internal fun PlaceBookmarkResponse.toDomainModel(): List<PlaceBookmark> {
    return data.map { placeBookmarkData ->
        PlaceBookmark(
            address = placeBookmarkData.address,
            disability = placeBookmarkData.disability,
            image = placeBookmarkData.image,
            name = placeBookmarkData.name,
            placeId = placeBookmarkData.placeId
        )
    }
}