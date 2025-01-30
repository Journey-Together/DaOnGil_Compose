package kr.techit.lion.network.response.navermap

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReverseGeocodeResponse(
    val results: List<Result?>?,
    val status: Status?
)