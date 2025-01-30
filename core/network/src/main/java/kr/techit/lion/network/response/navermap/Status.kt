package kr.techit.lion.network.response.navermap

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Status(
    val code: Int?,
    val message: String?,
    val name: String?
)
