package kr.techit.lion.network.response.scheduleform

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceSearchResultsResponse(
    val code: Int,
    val message: String,
    val data: PlaceSearchResultsData
)