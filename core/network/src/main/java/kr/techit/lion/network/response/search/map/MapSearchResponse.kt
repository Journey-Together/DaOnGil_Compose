package kr.techit.lion.network.response.search.map

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MapSearchResponse(
    val code: Int,
    val data: List<PlaceRes>,
    val message: String
)
