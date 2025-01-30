package kr.techit.lion.network.response.plan.mymainschedule

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val endDate: String,
    val hasReview: Boolean?,
    val imageUrl: String?,
    val planId: Long,
    val remainDate: String?,
    val startDate: String?,
    val title: String?
)
