package kr.techit.lion.network.response.plan.mymainschedule

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyMainScheduleResponse(
    val code: Int,
    val data: List<Data?>?,
    val message: String
)
