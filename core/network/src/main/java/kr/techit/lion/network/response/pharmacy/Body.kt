package kr.techit.lion.network.response.pharmacy

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Body(
    val items: Items,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)