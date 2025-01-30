package kr.techit.lion.network.response.pharmacy

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PharmacyResponse(
    val response: Response
)