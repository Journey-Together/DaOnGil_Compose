package kr.techit.lion.network.service

import kr.techit.lion.network.BuildConfig
import kr.techit.lion.network.response.pharmacy.PharmacyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PharmacyService {
    @GET("getParmacyListInfoInqire")
    suspend fun getPharmacy(
        @Query("Q0") q0: String?,
        @Query("Q1") q1: String?,
        @Query("numOfRows") numOfRows: Int = 24534,
        @Query("_type") type: String = "json",
        @Query("serviceKey") serviceKey: String = BuildConfig.EMERGENCY_API_KEY
    ) : PharmacyResponse
}