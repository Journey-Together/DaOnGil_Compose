package kr.techit.lion.network.response.search.keyword

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "keyword")
    val keyword: String,
    @Json(name = "placeId")
    val placeId: Long
)