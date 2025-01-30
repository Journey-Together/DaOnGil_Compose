package kr.techit.lion.data.mapper

import kr.techit.lion.database.entity.AreaCodeEntity
import kr.techit.lion.domain.model.area.AreaCode

internal fun AreaCodeEntity.toDomainModel(): AreaCode {
    return AreaCode(
        code = code,
        name = name
    )
}