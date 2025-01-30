package kr.techit.lion.network.response.pharmacy

import com.squareup.moshi.JsonClass
import kr.techit.lion.network.response.pharmacy.Body
import kr.techit.lion.network.response.pharmacy.Header

@JsonClass(generateAdapter = true)
data class Response(
    val body: Body,
    val header: Header
)