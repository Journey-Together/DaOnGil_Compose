package kr.techit.lion.network.response.mainplace

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AroundPlaceRes(
    val address: String,
    val disability: List<String>,
    val image: String,
    val name: String,
    val placeId: Long
)