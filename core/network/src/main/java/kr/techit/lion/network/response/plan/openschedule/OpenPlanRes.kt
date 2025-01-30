package kr.techit.lion.network.response.plan.openschedule

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenPlanRes(
    val date: String,
    val imageUrl: String?,
    val memberId: Int,
    val memberImageUrl: String,
    val memberNickname: String,
    val planId: Long,
    val title: String
)
