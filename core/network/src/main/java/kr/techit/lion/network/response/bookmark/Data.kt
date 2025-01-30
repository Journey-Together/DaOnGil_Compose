package kr.techit.lion.network.response.bookmark

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val state: Boolean
)