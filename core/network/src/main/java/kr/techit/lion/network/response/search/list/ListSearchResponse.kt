package kr.techit.lion.network.response.search.list

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchPlaceResponse(
    val code: Int,
    val data: Data,
    val message: String
)


