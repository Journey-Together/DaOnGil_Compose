package kr.techit.lion.network.response.detailplace

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubDisabilityRes(
    val description: String?,
    val subDisabilityName: String
)