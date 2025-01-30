package kr.techit.lion.network.response.bookmark

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceBookmarkListResponse(
    val code: Int,
    val data : List<PlaceBookmarkListData>,
    val message: String
)