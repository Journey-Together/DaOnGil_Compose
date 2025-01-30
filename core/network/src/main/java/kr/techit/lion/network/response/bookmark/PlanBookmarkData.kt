package kr.techit.lion.network.response.bookmark

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlanBookmarkData(
    val image: String?,
    val name: String,
    val planId: Long,
    val profileImg: String,
    val title: String
)