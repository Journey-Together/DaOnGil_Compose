package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.PlanDetailBookmark
import kr.techit.lion.network.response.bookmark.PlanDetailBookmarkResponse

internal fun PlanDetailBookmarkResponse.toDomainModel(): PlanDetailBookmark {
    return PlanDetailBookmark(
        state = this.data.state
    )
}