package kr.techit.lion.network.response.member

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyInfoResponse (
    val code: Int,
    val message: String,
    val data: MyInfoData
)