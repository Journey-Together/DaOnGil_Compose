package kr.techit.lion.data.datasource

import kr.techit.lion.database.dao.AreaCodeDao
import kr.techit.lion.database.entity.AreaCodeEntity
import javax.inject.Inject

internal class AreaCodeDataSource @Inject constructor(
    private val areaCodeDao: AreaCodeDao,
) {
    suspend fun getAllAreaCodes(): List<AreaCodeEntity> {
        return areaCodeDao.getAreaCodes()
    }

    fun getAreaCodeInfo(code: String): String? {
        return areaCodeDao.getAreaCode(code)
    }

    suspend fun addAreaCodeInfoList(areaCodeEntity: List<AreaCodeEntity>) {
      areaCodeDao.insertAreaCode(areaCodeEntity)
    }
}