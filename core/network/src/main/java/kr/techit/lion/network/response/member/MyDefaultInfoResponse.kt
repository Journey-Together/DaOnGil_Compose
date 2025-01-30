package kr.techit.lion.network.response.member

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyDefaultInfoResponse(
    val code: Int,
    val data: MyDefaultInfoData,
    val message: String
)