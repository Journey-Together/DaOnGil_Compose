package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.DailyPlan
import kr.techit.lion.network.response.plan.scheduledetailinfo.Daily

internal fun Daily.toDomainModel(): DailyPlan {
    return DailyPlan(
        dailyPlanDate = date,
        schedulePlaces = dailyPlaceInfoList?.map { it.toDomainModel() } ?: emptyList()
    )
}