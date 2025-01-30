package kr.techit.lion.network.response.member

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConcernTypeResponse(
    val code: Int,
    val data: ConcernTypeData,
    val message: String
)