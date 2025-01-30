package kr.techit.lion.network.response.plan.scheduledetailinfo

data class Data(
    val dailyList: List<Daily>,
    val endDate: String,
    val imageUrls: List<String>?,
    val isPublic: Boolean,
    val isWriter: Boolean,
    val remainDate: String?,
    val startDate: String,
    val title: String,
    val writerId: Long,
    val writerNickname: String
)
