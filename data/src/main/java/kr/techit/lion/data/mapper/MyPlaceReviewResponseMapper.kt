package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.MyPlaceReview
import kr.techit.lion.domain.model.MyPlaceReviewInfo
import kr.techit.lion.network.response.myreview.MyPlaceReviewResponse

internal fun MyPlaceReviewResponse.toDomainModel(): MyPlaceReview {
    return MyPlaceReview(
        myPlaceReviewInfoList = data.myPlaceReviewDtoList.map {
            MyPlaceReviewInfo(
                content = it.content,
                date = it.date,
                grade = it.grade,
                images = it.images,
                isReport = it.isReport,
                name = it.name,
                placeId = it.placeId,
                reviewId = it.reviewId
            )
        },
        pageNo = data.pageNo,
        pageSize = data.pageSize,
        reviewNum = data.reviewNum,
        totalPages = data.totalPages
    )
}