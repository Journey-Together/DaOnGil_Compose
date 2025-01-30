package kr.techit.lion.network.response.member

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyInfoData(
    val name: String,
    val nickname: String,
    val phone: String?,
    val profileImage: String?,
    val bloodType: String?,
    val birth: String?,
    val disease: String?,
    val allergy: String?,
    val medication: String?,
    @Json(name = "part1_rel")
    val part1Rel: String?,
    @Json(name = "part1_phone")
    val part1Phone: String?,
    @Json(name = "part2_rel")
    val part2Rel: String?,
    @Json(name = "part2_phone")
    val part2Phone: String?,
)