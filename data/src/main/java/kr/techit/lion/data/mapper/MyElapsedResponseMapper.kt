package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.schedule.MyElapsedScheduleInfo
import kr.techit.lion.domain.model.schedule.MyElapsedSchedules
import kr.techit.lion.network.response.plan.myscheduleelapsed.MyElapsedResponse

internal fun MyElapsedResponse.toDomainModel(): MyElapsedSchedules {
    return MyElapsedSchedules(
        myElapsedScheduleList = data.planResList.map {
            MyElapsedScheduleInfo(
                planId = it.planId,
                title = it.title,
                startDate = it.startDate,
                endDate = it.endDate,
                imageUrl = it.imageUrl ?: "",
                hasReview = it.hasReview
            )
        },
        last = data.last
    )
}