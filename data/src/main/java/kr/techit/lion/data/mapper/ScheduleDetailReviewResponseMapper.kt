package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.ScheduleDetailReview
import kr.techit.lion.network.response.plan.scheduledetailreview.ScheduleDetailReviewResponse

internal fun ScheduleDetailReviewResponse.toDomainModel(): ScheduleDetailReview {
    return ScheduleDetailReview(
        reviewId = data.reviewId,
        content = data.content,
        grade = data.grade,
        imageList = data.imageList,
        isWriter = data.isWriter,
        hasReview = data.hasReview,
        profileUrl = data.profileUrl,
        isReport = data.isReport
    )
}