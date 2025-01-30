package kr.techit.lion.network.service

import kr.techit.lion.network.response.areacode.AreaCodeResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface KorWithService {
    @GET("areaCode1")
    suspend fun getAreaCode(
        @QueryMap params: Map<String, String>
    ): AreaCodeResponse
}