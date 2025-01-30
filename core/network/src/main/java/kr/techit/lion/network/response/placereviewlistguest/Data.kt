package kr.techit.lion.network.response.placereviewlistguest

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kr.techit.lion.network.response.placereviewlist.MyPlaceReview

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "myplaceReviewList")
    val myPlaceReviewList: List<MyPlaceReview>,
    val placeImg: String,
    val pageNo: Int?,
    val pageSize: Int?,
    val placeAddress: String,
    val placeName: String,
    val totalPages: Int?,
    val reviewNum: Int
)