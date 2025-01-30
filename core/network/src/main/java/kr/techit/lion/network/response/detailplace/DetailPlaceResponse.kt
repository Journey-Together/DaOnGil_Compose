package kr.techit.lion.network.response.detailplace

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailPlaceResponse(
    val code: Int,
    val data: Data,
    val message: String
)