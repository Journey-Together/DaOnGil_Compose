package kr.techit.lion.network.response.plan.briefscheduleinfo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BriefScheduleInfoResponse(
    val code: Int,
    val message: String,
    val data: Data?
)

