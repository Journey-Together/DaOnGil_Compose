package kr.techit.lion.network.response.emergency.realtime

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmergencyRealtimeResponse(
    val response: Response
)