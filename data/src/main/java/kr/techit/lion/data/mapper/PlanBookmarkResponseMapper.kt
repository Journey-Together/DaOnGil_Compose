package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.PlanBookmark
import kr.techit.lion.network.response.bookmark.PlanBookmarkResponse

internal fun PlanBookmarkResponse.toDomainModel(): List<PlanBookmark> {
    return data.map { planBookmarkData ->
        PlanBookmark(
            image = planBookmarkData.image,
            name = planBookmarkData.name,
            planId = planBookmarkData.planId,
            profileImg = planBookmarkData.profileImg,
            title = planBookmarkData.title
        )
    }
}