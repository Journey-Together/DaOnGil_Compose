package kr.techit.lion.network.response.emergency.basic

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmergencyBasicResponse(
    val response: Response
)