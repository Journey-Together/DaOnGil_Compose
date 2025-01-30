package kr.techit.lion.network.response.bookmark

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlanBookmarkResponse(
    val code: Int,
    val data: List<PlanBookmarkData>,
    val message: String
)