package kr.techit.lion.network.response.myreview

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyPlaceReviewResponse(
    val code: Int,
    val data: MyPlaceReviewData,
    val message: String
)