package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.MyMainSchedule
import kr.techit.lion.network.response.plan.mymainschedule.MyMainScheduleResponse

internal fun MyMainScheduleResponse.toDomainModel(): List<MyMainSchedule?>? {
    return data?.map {
        MyMainSchedule(
            endDate = it?.endDate,
            hasReview = it?.hasReview,
            imageUrl = it?.imageUrl,
            planId = it?.planId,
            remainDate = it?.remainDate,
            startDate = it?.startDate,
            title = it?.title,
        )
    }
}
