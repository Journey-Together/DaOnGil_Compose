package kr.techit.lion.presentation.compose.screen.search.list.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.persistentListOf
import kr.techit.lion.presentation.compose.screen.search.list.model.City
import kr.techit.lion.presentation.compose.screen.search.list.model.City.AreaModel
import kr.techit.lion.presentation.compose.screen.search.list.model.City.SigunguModel

class CityParameterProvider : PreviewParameterProvider<City> {
    override val values = sequenceOf(
        AreaModel(
            persistentListOf(
                "서울특별시", "인천광역시", "대전광역시", "대구광역시", "광주광역시", "부산광역시",
                "울산광역시", "세종특별자치시", "경기도", "강원특별자치도", "충청북도", "충청남도",
                "경상북도", "경상남도", "전북특별자치도", "전라남도"
            )
        ),
        SigunguModel(
            persistentListOf(
                "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구",
                "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구",
                "용산구", "은평구", "종로구", "중구", "중랑구", "강화군", "계양구", "미추홀구", "남동구", "동구",
                "부평구", "서구", "연수구", "옹진군", "대덕구", "동구", "서구", "유성구", "중구", "남구", "달서구",
                "달성군", "동구", "북구", "서구", "수성구", "중구", "군위군", "광산구"
            )
        )
    )
}