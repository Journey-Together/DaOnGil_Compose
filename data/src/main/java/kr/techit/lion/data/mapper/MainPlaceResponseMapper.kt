package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.mainplace.AroundPlace
import kr.techit.lion.domain.model.mainplace.PlaceMainInfo
import kr.techit.lion.domain.model.mainplace.RecommendPlace
import kr.techit.lion.network.response.mainplace.MainPlaceResponse

internal fun MainPlaceResponse.toDomainModel(): PlaceMainInfo {
    return PlaceMainInfo(
        aroundPlaceList = data.aroundPlaceList.map {
            AroundPlace(
                address = it.address,
                disability = it.disability,
                image = it.image,
                name = it.name,
                placeId = it.placeId)
        },
        recommendPlaceList = data.recommendPlaceList.map {
            RecommendPlace(
                address = it.address,
                disability = it.disability,
                image = it.image,
                name = it.name,
                placeId = it.placeId
            )
        }
    )
}