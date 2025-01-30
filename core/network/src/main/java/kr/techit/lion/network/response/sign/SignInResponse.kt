package kr.techit.lion.network.response.sign

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInResponse(
    val code: Int,
    val data: Data,
    val message: String
)