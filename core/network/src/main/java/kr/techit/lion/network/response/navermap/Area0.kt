package kr.techit.lion.network.response.navermap

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Area0(
    val coords: Coords?,
    val name: String?
)
