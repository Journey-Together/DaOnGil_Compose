package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.placereviewlist.PlaceReview
import kr.techit.lion.domain.model.placereviewlist.PlaceReviewInfo
import kr.techit.lion.network.response.placereviewlist.PlaceReviewResponse

internal fun PlaceReviewResponse.toDomainModel(): PlaceReviewInfo {
    return PlaceReviewInfo(
        placeReviewList = data.myPlaceReviewList.map {
            PlaceReview(
                content = it.content,
                date = it.date,
                grade = it.grade,
                imageList = it.imageList,
                nickname = it.nickname,
                profileImg = it.profileImg,
                reviewId = it.reviewId,
                myReview = it.myReview
            )
        },
        placeImg = data.placeImg,
        pageNo = data.pageNo,
        pageSize = data.pageSize,
        placeAddress = data.placeAddress,
        placeName = data.placeName,
        totalPages = data.totalPages,
        reviewNum = data.reviewNum
    )
}