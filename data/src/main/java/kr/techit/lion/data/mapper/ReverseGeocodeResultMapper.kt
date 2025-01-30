package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.ReverseGeocode
import kr.techit.lion.network.response.navermap.Result

internal fun Result.toDomainModel(): ReverseGeocode {
    return ReverseGeocode(
        area = region?.area1?.name,
        areaDetail = region?.area2?.name
    )
}