package kr.techit.lion.network.response.navermap

import com.squareup.moshi.JsonClass
import kr.techit.lion.network.response.navermap.Center

@JsonClass(generateAdapter = true)
data class Coords(
    val center: Center?
)
