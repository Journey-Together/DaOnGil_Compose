package kr.techit.lion.network.response.placereviewlistguest

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceReviewResponseGuest(
    val code: Int,
    val data: Data,
    val message: String
)