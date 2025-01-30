package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.search.AutoCompleteKeyword
import kr.techit.lion.network.response.search.keyword.AutoCompleteKeywordResponse

internal fun AutoCompleteKeywordResponse.toDomainModel(): List<AutoCompleteKeyword> {
    return data.map {
        AutoCompleteKeyword(
            placeName = it.keyword,
            placeId = it.placeId
        )
    }
}