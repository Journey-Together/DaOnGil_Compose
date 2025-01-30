package kr.techit.lion.network.response.plan.scheduledetailinfo


data class DailyPlaceInfo(
    val category: String,
    val imageUrl: String?,
    val disabilityCategoryList: List<Int>,
    val name: String,
    val placeId: Long
)
