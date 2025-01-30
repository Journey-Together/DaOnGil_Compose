package kr.techit.lion.data.datasource

import kr.techit.lion.data.mapper.toDomainModel
import kr.techit.lion.domain.model.AedMapInfo
import kr.techit.lion.network.service.AedService
import javax.inject.Inject

internal class AedDataSource @Inject constructor(
    private val aedService: AedService
) {
    suspend fun getAedInfo(q0: String?, q1: String?) : List<AedMapInfo> {
        return aedService.getAedInfo(q0, q1).toDomainModel()
    }
}