package kr.techit.lion.network.response.bookmark

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceBookmarkResponse(
    val code: Int,
    val data: List<PlaceBookmarkData>,
    val message: String
)