package kr.techit.lion.network.response.emergency.realtime

import com.squareup.moshi.JsonClass
import kr.techit.lion.data.dto.response.emergency.realtime.Body

@JsonClass(generateAdapter = true)
data class Response(
    val body: Body,
    val header: Header
)
