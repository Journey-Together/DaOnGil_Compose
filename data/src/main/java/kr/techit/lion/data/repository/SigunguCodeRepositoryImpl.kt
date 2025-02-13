package kr.techit.lion.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.techit.lion.data.datasource.SigunguCodeDatasource
import kr.techit.lion.data.mapper.toDomainModel
import kr.techit.lion.database.entity.SigunguCodeEntity
import kr.techit.lion.domain.model.area.SigunguCode
import kr.techit.lion.domain.model.area.SigunguCodeList
import kr.techit.lion.domain.repository.SigunguCodeRepository
import javax.inject.Inject

internal class SigunguCodeRepositoryImpl @Inject constructor(
    private val sigunguCodeDatasource: SigunguCodeDatasource
) : SigunguCodeRepository {

    override suspend fun getSigunguCodeByVillageName(villageName: String, areaCode: String): String? {
        return withContext(Dispatchers.IO) {
            sigunguCodeDatasource.getSigunguCodeByVillageName(villageName, areaCode)
        }
    }

    override suspend fun getAllSigunguCode(code: String): SigunguCodeList {
        return withContext(Dispatchers.IO) {
            SigunguCodeList(
                sigunguCodeDatasource.getAllSigunguInfoList(code).map {
                    it.toDomainModel()
                }
            )
        }
    }

    override suspend fun addSigunguCode(sigunguCode: List<SigunguCode>) {
        withContext(Dispatchers.IO) {
            sigunguCodeDatasource.addSigunguCodeInfoList(
                sigunguCode.map {
                    SigunguCodeEntity(
                        sigunguName = it.sigunguName,
                        sigunguCode = it.sigunguCode,
                        areaCode = it.areaCode
                    )
                }
            )
        }
    }
}