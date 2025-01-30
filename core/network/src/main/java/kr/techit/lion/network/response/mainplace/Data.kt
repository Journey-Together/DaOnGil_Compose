package kr.techit.lion.network.response.mainplace

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val aroundPlaceList: List<AroundPlaceRes>,
    val recommendPlaceList: List<RecommendPlaceRes>
)