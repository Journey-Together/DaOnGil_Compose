package kr.techit.lion.network.response.emergency.basic

import com.squareup.moshi.JsonClass
import kr.techit.lion.network.response.emergency.basic.Item

@JsonClass(generateAdapter = true)
data class Items(
    val item: Item
)
