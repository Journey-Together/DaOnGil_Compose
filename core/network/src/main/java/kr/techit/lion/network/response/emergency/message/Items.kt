package kr.techit.lion.network.response.emergency.message

import com.squareup.moshi.JsonClass
import kr.techit.lion.network.response.emergency.message.Item

@JsonClass(generateAdapter = true)
data class Items(
    val item: List<Item>
)
