package kr.techit.lion.network.response.sign

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpResponse(
    val accessToken: String,
    val refreshToken: String,
)