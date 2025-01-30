package kr.techit.lion.data.dto.response.emergency.realtime

import com.squareup.moshi.JsonClass
import kr.techit.lion.network.response.emergency.realtime.Items

@JsonClass(generateAdapter = true)
data class Body(
    val items: Items,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)
