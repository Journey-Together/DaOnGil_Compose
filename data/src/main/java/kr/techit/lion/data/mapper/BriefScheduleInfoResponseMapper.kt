package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.schedule.BriefScheduleInfo
import kr.techit.lion.network.response.plan.briefscheduleinfo.BriefScheduleInfoResponse

internal fun BriefScheduleInfoResponse.toDomainModel() : BriefScheduleInfo {
    return BriefScheduleInfo(
        planId = this.data?.planId,
        title = this.data?.title,
        startDate = this.data?.startDate,
        endDate = this.data?.endDate,
        imageUrl = this.data?.imageUrl
    )
}