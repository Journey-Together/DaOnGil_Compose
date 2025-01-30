package kr.techit.lion.network.response.aed

import com.squareup.moshi.JsonClass
import kr.techit.lion.network.response.aed.Body
import kr.techit.lion.network.response.aed.Header

@JsonClass(generateAdapter = true)
data class Response(
    val body: Body,
    val header: Header
)
