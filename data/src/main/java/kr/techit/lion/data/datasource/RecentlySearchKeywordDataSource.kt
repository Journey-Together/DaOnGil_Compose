package kr.techit.lion.data.datasource

import kotlinx.coroutines.flow.Flow
import kr.techit.lion.database.dao.RecentlySearchKeywordDAO
import kr.techit.lion.database.entity.RecentlySearchKeywordEntity
import javax.inject.Inject

internal class RecentlySearchKeywordDataSource @Inject constructor(
    private val recentlySearchKeywordDao: RecentlySearchKeywordDAO
) {
    fun readAllKeyword(): Flow<List<RecentlySearchKeywordEntity>> = recentlySearchKeywordDao.readAllKeyword()

    suspend fun insertKeyword(keyword: RecentlySearchKeywordEntity) = recentlySearchKeywordDao.insertKeyword(keyword)

    suspend fun deleteKeyword(id: Long) = recentlySearchKeywordDao.deleteKeyword(id)

    suspend fun deleteAllKeyword() = recentlySearchKeywordDao.deleteAllKeyword()
}