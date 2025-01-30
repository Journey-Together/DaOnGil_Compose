package kr.techit.lion.data.datasource

import kr.techit.lion.domain.model.ReverseGeocodes
import kr.techit.lion.domain.exception.Result
import kr.techit.lion.data.common.execute
import kr.techit.lion.data.mapper.toDomainModel
import kr.techit.lion.network.service.NaverMapService
import javax.inject.Inject

internal class NaverMapDataSource @Inject constructor(
    private val naverMapService: NaverMapService
) {
    suspend fun getReverseGeoCode(coords: String): Result<ReverseGeocodes> = execute {
        naverMapService.getReverseGeoCode(coords).toDomainModel()
    }
}