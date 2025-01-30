package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.BookmarkedPlace
import kr.techit.lion.network.response.bookmark.PlaceBookmarkListResponse

internal fun PlaceBookmarkListResponse.toDomainModel(): List<BookmarkedPlace> {
    return data.map { PlaceBookmarkListData ->
        BookmarkedPlace(
            bookmarkedPlaceId = PlaceBookmarkListData.placeId,
            bookmarkedPlaceName = PlaceBookmarkListData.placeName
        )
    }
}