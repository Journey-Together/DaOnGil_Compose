package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.ReverseGeocodes
import kr.techit.lion.network.response.navermap.ReverseGeocodeResponse

internal fun ReverseGeocodeResponse.toDomainModel(): ReverseGeocodes {
    val domainResults = results?.mapNotNull { it?.toDomainModel() } ?: emptyList()
    return ReverseGeocodes(
        code = status?.code,
        results = domainResults
    )
}