package kr.techit.lion.network.response.detailplaceguest

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailPlaceGuestResponse(
    val code: Int,
    val data: Data,
    val message: String
)