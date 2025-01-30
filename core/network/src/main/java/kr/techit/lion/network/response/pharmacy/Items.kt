package kr.techit.lion.network.response.pharmacy

import com.squareup.moshi.JsonClass
import kr.techit.lion.network.response.pharmacy.Item

@JsonClass(generateAdapter = true)
data class Items(
    val item: List<Item>
)