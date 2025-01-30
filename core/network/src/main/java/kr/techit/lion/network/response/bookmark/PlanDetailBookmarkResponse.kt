package kr.techit.lion.network.response.bookmark

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlanDetailBookmarkResponse(
    val code: Int,
    val data: Data,
    val message: String?
)