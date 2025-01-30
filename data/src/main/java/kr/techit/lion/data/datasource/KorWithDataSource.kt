package kr.techit.lion.data.datasource

import kr.techit.lion.data.dto.request.AreaCodeRequest
import kr.techit.lion.network.response.areacode.AreaCodeResponse
import kr.techit.lion.network.service.KorWithService
import javax.inject.Inject

internal class KorWithDataSource @Inject constructor(
    private val korWithService: KorWithService
) {
    suspend fun getAreaInfoList(code: String = ""): AreaCodeResponse {
        return korWithService.getAreaCode(AreaCodeRequest(code).toRequestModel())
    }
}