package kr.techit.lion.network.response.mainplace

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MainPlaceResponse(
    val code: Int,
    val data: Data,
    val message: String
)