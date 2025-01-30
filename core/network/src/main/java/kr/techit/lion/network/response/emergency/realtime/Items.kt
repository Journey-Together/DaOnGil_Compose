package kr.techit.lion.network.response.emergency.realtime

import com.squareup.moshi.JsonClass
import kr.techit.lion.network.response.emergency.realtime.Item

@JsonClass(generateAdapter = true)
data class Items(
    val item: List<Item>
)
