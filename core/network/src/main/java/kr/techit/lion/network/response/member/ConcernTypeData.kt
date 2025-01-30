package kr.techit.lion.network.response.member

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConcernTypeData(
    val isPhysical: Boolean,
    val isHear: Boolean,
    val isVisual: Boolean,
    val isElderly: Boolean,
    val isChild: Boolean,
)