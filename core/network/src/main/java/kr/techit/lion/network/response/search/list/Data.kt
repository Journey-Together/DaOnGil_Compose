package kr.techit.lion.network.response.search.list

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val pageNo: Int,
    val pageSize: Int,
    val placeResList: List<PlaceRes>,
    val totalSize: Int
)