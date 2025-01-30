package kr.techit.lion.network.response.placereviewlist

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceReviewResponse(
    val code: Int,
    val data: Data,
    val message: String
)