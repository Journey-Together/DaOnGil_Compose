package kr.techit.lion.network.response.navermap

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Code(
    val id: String?,
    val mappingId: String?,
    val type: String?
)
