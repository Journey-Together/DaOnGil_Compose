package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.ScheduleDetailInfo
import kr.techit.lion.network.response.plan.scheduledetailinfo.ScheduleDetailResponse

internal fun ScheduleDetailResponse.toDomainModel(): ScheduleDetailInfo {
    return data.let{
        ScheduleDetailInfo(
            title = it.title,
            startDate = it.startDate,
            endDate = it.endDate,
            remainDate = it.remainDate,
            isPublic = it.isPublic,
            isWriter = it.isWriter,
            nickname = it.writerNickname,
            images = it.imageUrls,
            writerId = it.writerId,
            dailyPlans = it.dailyList.map { it.toDomainModel() }
        )
    }
}