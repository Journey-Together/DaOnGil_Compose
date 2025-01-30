package kr.techit.lion.network.response.plan.briefscheduleinfo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val planId: Long,
    val title: String?,
    val startDate: String?,
    val endDate: String?,
    val imageUrl: String?,
)
