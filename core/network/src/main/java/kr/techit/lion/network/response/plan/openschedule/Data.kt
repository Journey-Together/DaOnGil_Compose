package kr.techit.lion.network.response.plan.openschedule

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val last: Boolean,
    val openPlanResList: List<OpenPlanRes>,
    val pageNo: Int,
    val pageSize: Int,
    val totalPages: Int
)
