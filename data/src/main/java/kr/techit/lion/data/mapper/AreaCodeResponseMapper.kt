package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.area.AreaCode
import kr.techit.lion.network.response.areacode.AreaCodeResponse

internal fun AreaCodeResponse.toDomainModel(): List<AreaCode> {
    return response.body.items.item.map {
        AreaCode(it.code, it.name)
    }
}