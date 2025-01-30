package kr.techit.lion.network.response.detailplace

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val address: String,
    val bookmarkNum: Int,
    val category: String,
    val disability: List<Int>,
    @Json(name = "imgae")
    val image: String?,
    val isMark: Boolean,
    val mapX: String,
    val mapY: String,
    val name: String,
    val overview: String,
    val tel: String?,
    val homepage: String?,
    val isReview: Boolean,
    val placeId: Long,
    val reviewList: List<ReviewRes>?,
    val subDisability: List<SubDisabilityRes>?
)