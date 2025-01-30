package kr.techit.lion.network.response.search.map

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class Data(
    val placeResList: List<PlaceRes>
)