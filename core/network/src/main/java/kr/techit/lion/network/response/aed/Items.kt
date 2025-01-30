package kr.techit.lion.network.response.aed

import com.squareup.moshi.JsonClass
import kr.techit.lion.network.response.aed.Item

@JsonClass(generateAdapter = true)
data class Items(
    val item: List<Item>
)
