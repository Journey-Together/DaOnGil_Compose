package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.SchedulePlace
import kr.techit.lion.network.response.plan.scheduledetailinfo.DailyPlaceInfo

internal fun DailyPlaceInfo.toDomainModel(): SchedulePlace {
    return SchedulePlace(
        placeId = placeId,
        name = name,
        category = category,
        imageUrl = imageUrl ?: "",
        disability = disabilityCategoryList
    )
}