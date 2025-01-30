package kr.techit.lion.network.response.emergency.message

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmergencyMessageResponse(
    val response: Response
)
