package kr.techit.lion.network.response.navermap

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Center(
    val crs: String?,
    val x: Double?,
    val y: Double?
)
