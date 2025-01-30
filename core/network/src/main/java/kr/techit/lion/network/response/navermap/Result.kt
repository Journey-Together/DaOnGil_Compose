package kr.techit.lion.network.response.navermap

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    val code: Code?,
    val name: String?,
    val region: Region?
)