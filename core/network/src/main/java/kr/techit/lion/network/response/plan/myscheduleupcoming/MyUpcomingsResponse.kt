package kr.techit.lion.network.response.plan.myscheduleupcoming

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyUpcomingsResponse(
    val code: Int,
    val message: String,
    val data: Data,
)