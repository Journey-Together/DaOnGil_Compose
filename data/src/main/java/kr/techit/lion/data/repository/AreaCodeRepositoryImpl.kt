package kr.techit.lion.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.techit.lion.data.datasource.AreaCodeDataSource
import kr.techit.lion.data.mapper.toDomainModel
import kr.techit.lion.data.mapper.toFullAreaName
import kr.techit.lion.database.entity.AreaCodeEntity
import kr.techit.lion.domain.model.area.AreaCode
import kr.techit.lion.domain.model.area.AreaCodeList
import kr.techit.lion.domain.repository.AreaCodeRepository
import javax.inject.Inject

internal class AreaCodeRepositoryImpl @Inject constructor(
    private val areaCodeDataSource: AreaCodeDataSource,
) : AreaCodeRepository {

    // 이름으로 지역코드 검색
    override suspend fun getAreaCodeByName(areaName: String): String? {
        return withContext(Dispatchers.IO) {
            areaCodeDataSource.getAreaCodeInfo(areaName)
        }
    }

    // 로컬의 모든 지역코드
    override suspend fun getAllAreaCodes(): AreaCodeList {
        return withContext(Dispatchers.IO) {
            AreaCodeList(
                areaCodeDataSource.getAllAreaCodes().map {
                    it.toDomainModel()
                }
            )
        }
    }

    override suspend fun addAreaCodeInfo(areaCodeList: List<AreaCode>) {
        withContext(Dispatchers.IO) {
            areaCodeDataSource.addAreaCodeInfoList(
                areaCodeList.map {
                    AreaCodeEntity(
                        code = it.code,
                        name = it.name.toFullAreaName()
                    )
                }
            )
        }
    }
}