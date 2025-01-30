package kr.techit.lion.data.mapper

import kr.techit.lion.domain.model.MyInfo
import kr.techit.lion.network.response.member.MyInfoResponse

internal fun MyInfoResponse.toDomainModel(): MyInfo {
    return MyInfo(
        name = data.name,
        nickname = data.nickname,
        phone = data.phone,
        profileImage = data.profileImage,
        bloodType = data.bloodType,
        birth = data.birth,
        disease = data.disease,
        allergy = data.allergy,
        medication = data.medication,
        part1Rel = data.part1Rel,
        part1Phone = data.part1Phone,
        part2Rel = data.part2Rel,
        part2Phone = data.part2Phone
    )
}