package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.ConcernType
import kr.techit.lion.network.response.member.ConcernTypeResponse

internal fun ConcernTypeResponse.toDomainModel(): ConcernType {
    return ConcernType(
        isPhysical = data.isPhysical,
        isHear = data.isHear,
        isVisual = data.isVisual,
        isElderly = data.isElderly,
        isChild = data.isChild
    )
}