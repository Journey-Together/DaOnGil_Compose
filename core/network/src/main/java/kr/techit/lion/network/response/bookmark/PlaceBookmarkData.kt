package kr.techit.lion.network.response.bookmark

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceBookmarkData(
    val address: String,
    val disability: List<String>,
    val image: String,
    val name: String,
    val placeId: Long
)