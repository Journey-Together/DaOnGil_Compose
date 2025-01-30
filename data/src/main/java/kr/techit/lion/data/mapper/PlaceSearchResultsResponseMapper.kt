package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.scheduleform.PlaceSearchInfoList
import kr.techit.lion.domain.model.scheduleform.PlaceSearchResult
import kr.techit.lion.network.response.scheduleform.PlaceSearchResultsResponse

internal fun PlaceSearchResultsResponse.toDomainModel() : PlaceSearchResult {
    return PlaceSearchResult(
        placeInfoList = this.data.placeInfoList.map {
            PlaceSearchInfoList.PlaceSearchInfo(
                placeId = it.placeId,
                placeName = it.placeName,
                category = it.category,
                imageUrl = it.imageUrl
            )
        },
        pageNo = this.data.pageNo,
        last = this.data.last,
        PlaceSearchInfoList.TotalElementsInfo(
            totalElements = this.data.totalElements
        )
    )
}