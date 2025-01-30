package kr.techit.lion.network.response.emergency.message

import com.squareup.moshi.JsonClass
import kr.techit.lion.network.response.emergency.message.Body
import kr.techit.lion.network.response.emergency.message.Header

@JsonClass(generateAdapter = true)
data class Response(
    val body: Body,
    val header: Header
)
