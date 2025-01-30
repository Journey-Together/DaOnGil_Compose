package kr.techit.lion.network.response.navermap

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Region(
    val area0: Area0?,
    val area1: Area0?,
    val area2: Area0?,
    val area3: Area0?,
    val area4: Area0?
)