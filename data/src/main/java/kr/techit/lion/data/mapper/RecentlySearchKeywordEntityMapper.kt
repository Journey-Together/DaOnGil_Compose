package kr.techit.lion.data.mapper

import kr.techit.lion.database.entity.RecentlySearchKeywordEntity
import kr.techit.lion.domain.model.search.RecentlySearchKeyword

internal fun RecentlySearchKeywordEntity.toDomainModel(): RecentlySearchKeyword {
    return RecentlySearchKeyword(
        id = id,
        keyword = keyword
    )
}