package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.detailplace.PlaceDetailInfoGuest
import kr.techit.lion.domain.model.detailplace.Review
import kr.techit.lion.domain.model.detailplace.SubDisability
import kr.techit.lion.network.response.detailplaceguest.DetailPlaceGuestResponse

internal fun DetailPlaceGuestResponse.toDomainModel(): PlaceDetailInfoGuest {
    return PlaceDetailInfoGuest(
        code = code,
        address = data.address,
        bookmarkNum = data.bookmarkNum,
        category = data.category,
        disability = data.disability,
        image = data.image,
        latitude = data.mapX,
        longitude = data.mapY,
        name = data.name,
        overview = data.overview,
        tel = data.tel.orEmpty(),
        homepage = data.homepage.orEmpty(),
        placeId = data.placeId,
        reviewList = data.reviewList?.map {
            Review(
                reviewImgs = it.reviewImgs?: emptyList(),
                nickname = it.nickname,
                profileImg = it.profileImg,
                content = it.content,
                reviewId = it.reviewId,
                grade = it.grade,
                date = it.date,
                myReview = it.myReview
            )
        },
        subDisability = data.subDisability?.map {
            SubDisability(
                description = it.description,
                subDisabilityName = it.subDisabilityName
            )
        }
    )
}
