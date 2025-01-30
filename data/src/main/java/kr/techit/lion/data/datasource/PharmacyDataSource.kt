package kr.techit.lion.data.datasource

import kr.techit.lion.domain.model.PharmacyMapInfo
import kr.techit.lion.domain.exception.Result
import kr.techit.lion.data.common.execute
import kr.techit.lion.data.mapper.toDomainModel
import kr.techit.lion.network.service.PharmacyService
import javax.inject.Inject

internal class PharmacyDataSource @Inject constructor(
    private val pharmacyService: PharmacyService
) {
    suspend fun getPharmacy(q0: String?, q1: String?): Result<List<PharmacyMapInfo>> = execute {
        pharmacyService.getPharmacy(q0, q1).toDomainModel()
    }
}