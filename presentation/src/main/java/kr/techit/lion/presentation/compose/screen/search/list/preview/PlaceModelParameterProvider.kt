package kr.techit.lion.presentation.compose.screen.search.list.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kr.techit.lion.presentation.compose.screen.search.list.model.PlaceModel

class PlaceModelParameterProvider : PreviewParameterProvider<List<PlaceModel>> {
    override val values = sequenceOf(
        listOf(
            PlaceModel(
                placeName = "달빛곳간",
                placeAddr = "전북특별자치도 임실군 삼계면 세심길 26",
                placeId = 3354268,
                placeImg = "http://tong.visitkorea.or.kr/cms/resource/31/3354231_image2_1.png",
                disability = listOf("1", "2"),
                itemCount = 5640
            ),
            PlaceModel(
                placeName = "오로시예술공방",
                placeAddr = "전라남도 화순군 능주면 잠정햇살길 22",
                placeId = 3354209,
                placeImg = "http://tong.visitkorea.or.kr/cms/resource/81/3354181_image2_1.jpg",
                disability = listOf("1"),
                itemCount = 5640
            ),
            PlaceModel(
                placeName = "삼포 만화북투어",
                placeAddr = "인천광역시 동구 어촌로5번길 3-3 (만석동, 만석3 우리집)",
                placeId = 3354154,
                placeImg = "http://tong.visitkorea.or.kr/cms/resource/88/3354088_image2_1.jpg",
                disability = listOf("1", "2", "4"),
                itemCount = 5640
            ),
            PlaceModel(
                placeName = "소잉",
                placeAddr = "광주광역시 북구 첨단연신로108번길 31-15 (신용동)",
                placeId = 3353909,
                placeImg = "http://tong.visitkorea.or.kr/cms/resource/08/3353808_image2_1.jpg",
                disability = listOf("1", "2"),
                itemCount = 5640
            ),
            PlaceModel(
                placeName = "꾸꾸네",
                placeAddr = "경상북도 청도군 운문면 운문로 1551",
                placeId = 3353714,
                placeImg = "http://tong.visitkorea.or.kr/cms/resource/99/3353599_image2_1.jpg",
                disability = listOf("1", "2"),
                itemCount = 5640
            ),
            PlaceModel(
                placeName = "레인보우힐링센터",
                placeAddr = "충청북도 영동군 영동읍 영동힐링로 95",
                placeId = 3346305,
                placeImg = "http://tong.visitkorea.or.kr/cms/resource/85/3346285_image2_1.jpg",
                disability = listOf("1", "2", "4"),
                itemCount = 5640
            ),
            PlaceModel(
                placeName = "완주 고산공원 무궁화테마식물원",
                placeAddr = "전북특별자치도 완주군 고산면 고산휴양림로 89",
                placeId = 3345112,
                placeImg = "http://tong.visitkorea.or.kr/cms/resource/92/3345092_image2_1.jpg",
                disability = listOf("1", "2", "4"),
                itemCount = 5640
            ),
            PlaceModel(
                placeName = "가파도 소망전망대",
                placeAddr = "제주특별자치도 서귀포시 대정읍 가파리",
                placeId = 3344437,
                placeImg = "http://tong.visitkorea.or.kr/cms/resource/99/3344399_image2_1.jpg",
                disability = listOf("1"),
                itemCount = 5640
            ),
            PlaceModel(
                placeName = "대봉캠핑랜드",
                placeAddr = "경상남도 함양군 병곡면 원산지소길 192",
                placeId = 3344166,
                placeImg = "http://tong.visitkorea.or.kr/cms/resource/28/3344128_image2_1.jpg",
                disability = listOf("1", "2"),
                itemCount = 5640
            ),
            PlaceModel(
                placeName = "소도야영장",
                placeAddr = "강원특별자치도 태백시 천제단길 181 (소도동)",
                placeId = 3344099,
                placeImg = "http://tong.visitkorea.or.kr/cms/resource/87/3344087_image2_1.jpg",
                disability = listOf("1", "2"),
                itemCount = 5640
            )
        )
    )
}