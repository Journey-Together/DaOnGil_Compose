package kr.techit.lion.network.response.areacode

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AreaCodeResponse(
    val response: Response
)




