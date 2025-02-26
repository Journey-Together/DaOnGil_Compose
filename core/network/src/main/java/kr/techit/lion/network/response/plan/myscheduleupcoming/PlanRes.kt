package kr.techit.lion.network.response.plan.myscheduleupcoming

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlanRes(
    val planId: Long,
    val title: String,
    val startDate: String,
    val endDate: String,
    val imageUrl: String?,
     val remainDate: String,
)