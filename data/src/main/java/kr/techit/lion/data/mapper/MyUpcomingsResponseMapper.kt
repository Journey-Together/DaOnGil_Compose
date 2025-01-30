package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.schedule.MyUpcomingScheduleInfo
import kr.techit.lion.domain.model.schedule.MyUpcomingSchedules
import kr.techit.lion.network.response.plan.myscheduleupcoming.MyUpcomingsResponse

internal fun MyUpcomingsResponse.toDomainModel(): MyUpcomingSchedules {
    return MyUpcomingSchedules(
        myUpcomingScheduleList = this.data.planResList.map {
            MyUpcomingScheduleInfo(
                planId = it.planId,
                title = it.title,
                startDate = it.startDate,
                endDate = it.endDate,
                imageUrl = it.imageUrl ?: "",
                remainDate = it.remainDate
            )
        },
        last = data.last
    )
}
