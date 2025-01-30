package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.OpenPlan
import kr.techit.lion.domain.model.OpenPlanInfo
import kr.techit.lion.network.response.plan.openschedule.OpenPlanListResponse

internal fun OpenPlanListResponse.toDomainModel(): OpenPlan {
    return OpenPlan(
        last = this.data.last,
        pageNo = this.data.pageNo,
        totalPages = this.data.totalPages,
        openPlanList = this.data.openPlanResList.map {
            OpenPlanInfo(
                date = it.date,
                imageUrl = it.imageUrl,
                memberId = it.memberId,
                memberImageUrl = it.memberImageUrl,
                memberNickname = it.memberNickname,
                planId = it.planId,
                title = it.title
            )
        }
    )
}
