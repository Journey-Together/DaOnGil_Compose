package kr.techit.lion.network.response.search.keyword

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AutoCompleteKeywordResponse(
    val code: Int,
    val data: List<Data>,
    val message: String
)