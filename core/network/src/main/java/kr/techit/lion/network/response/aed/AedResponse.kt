package kr.techit.lion.network.response.aed

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AedResponse(
    val response: Response
)