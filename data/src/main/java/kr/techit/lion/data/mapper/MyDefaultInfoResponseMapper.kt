package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.MyDefaultInfo
import kr.techit.lion.network.response.member.MyDefaultInfoResponse

internal fun MyDefaultInfoResponse.toDomainModel(): MyDefaultInfo {
    return MyDefaultInfo(
        date = data.date,
        name = data.name,
        profileImg = data.profileImg,
        reviewNum = data.reviewNum
    )
}