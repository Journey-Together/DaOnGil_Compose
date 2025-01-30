package kr.techit.lion.network.response.emergency.basic

import com.squareup.moshi.JsonClass
import kr.techit.lion.network.response.emergency.basic.Body
import kr.techit.lion.network.response.emergency.basic.Header

@JsonClass(generateAdapter = true)
data class Response(
    val body: Body,
    val header: Header
)
