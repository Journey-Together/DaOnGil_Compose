package kr.techit.lion.network.response.myreview

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyPlaceReviewData(
    val myPlaceReviewDtoList: List<MyPlaceReviewDto>,
    val pageNo: Int,
    val pageSize: Int,
    val reviewNum: Long,
    val totalPages: Int
)