package kr.techit.lion.network.response.plan.scheduledetailinfo

data class Daily(
    val dailyPlaceInfoList: List<DailyPlaceInfo>?,
    val date: String
)