package kr.techit.lion.network.service

import kr.techit.lion.network.BuildConfig
import kr.techit.lion.network.response.aed.AedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AedService {
    @GET("getEgytAedManageInfoInqire")
    suspend fun getAedInfo(
        @Query("Q0") q0: String?,
        @Query("Q1") q1: String?,
        @Query("numOfRows") numOfRows: Int = 48568,
        @Query("_type") type: String = "json",
        @Query("serviceKey") serviceKey: String = BuildConfig.EMERGENCY_API_KEY
    ) : AedResponse
}