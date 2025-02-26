package kr.techit.lion.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.techit.lion.data.datasource.RecentlySearchKeywordDataSource
import kr.techit.lion.data.mapper.toDomainModel
import kr.techit.lion.database.entity.RecentlySearchKeywordEntity
import kr.techit.lion.domain.model.search.RecentlySearchKeywordList
import kr.techit.lion.domain.repository.RecentlySearchKeywordRepository
import javax.inject.Inject

internal class RecentlySearchKeywordRepositoryImpl @Inject constructor(
    private val recentlySearchKeywordDataSource: RecentlySearchKeywordDataSource,
) : RecentlySearchKeywordRepository {

    override val savedKeyword: Flow<RecentlySearchKeywordList>
        get() = recentlySearchKeywordDataSource
            .readAllKeyword()
            .map { list ->
                RecentlySearchKeywordList(
                    list.map { it.toDomainModel() }
                )
            }

    override suspend fun insertKeyword(keyword: String) {
        recentlySearchKeywordDataSource.insertKeyword(RecentlySearchKeywordEntity(keyword))
    }

    override suspend fun deleteKeyword(id: Long) {
        recentlySearchKeywordDataSource.deleteKeyword(id)
    }

    override suspend fun deleteAllKeyword() {
        recentlySearchKeywordDataSource.deleteAllKeyword()
    }
}