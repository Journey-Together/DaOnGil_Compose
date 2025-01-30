package kr.techit.lion.network.response.detailplaceguest

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubDisabilityGuestRes (
    val description: String?,
    val subDisabilityName: String
)