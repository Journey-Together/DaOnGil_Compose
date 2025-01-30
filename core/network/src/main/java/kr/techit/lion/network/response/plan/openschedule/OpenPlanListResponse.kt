package kr.techit.lion.network.response.plan.openschedule

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenPlanListResponse(
    val code: Int,
    val data: Data,
    val message: String
)
