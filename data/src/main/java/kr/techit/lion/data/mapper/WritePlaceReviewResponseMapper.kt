package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.placereview.WritePlaceReview
import kr.techit.lion.network.response.placereview.WritePlaceReviewResponse

internal fun WritePlaceReviewResponse.toDomainModel(): WritePlaceReview {
    return WritePlaceReview(
        code = code
    )
}