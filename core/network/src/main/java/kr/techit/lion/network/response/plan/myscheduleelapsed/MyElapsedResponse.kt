package kr.techit.lion.network.response.plan.myscheduleelapsed

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyElapsedResponse(
    val code: Int,
    val message: String,
    val data: Data,
)