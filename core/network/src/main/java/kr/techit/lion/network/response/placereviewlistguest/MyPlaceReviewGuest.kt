package kr.techit.lion.network.response.placereviewlistguest

import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class MyPlaceReviewGuest (
    val content: String,
    val date: LocalDate,
    val grade: Float,
    val imageList: List<String>,
    val nickname: String,
    val profileImg: String,
    val reviewId: Int,
    val myReview: Boolean
)