package kr.techit.lion.data.mapper

import kr.techit.lion.database.entity.SigunguCodeEntity
import kr.techit.lion.domain.model.area.SigunguCode

internal fun SigunguCodeEntity.toDomainModel(): SigunguCode {
    return SigunguCode(
        sigunguName = sigunguName,
        sigunguCode = sigunguCode,
        areaCode = areaCode
    )
}