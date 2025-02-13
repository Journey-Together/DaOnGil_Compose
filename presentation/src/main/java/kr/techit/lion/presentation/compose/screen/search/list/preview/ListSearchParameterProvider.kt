package kr.techit.lion.presentation.compose.screen.search.list.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kr.techit.lion.presentation.compose.screen.search.list.model.City
import kr.techit.lion.presentation.compose.screen.search.list.model.City.AreaModel
import kr.techit.lion.presentation.compose.screen.search.list.model.ListOptionStatus
import kr.techit.lion.presentation.compose.screen.search.list.model.ListSearchUiState
import kr.techit.lion.presentation.compose.screen.search.model.CategoryStatus
import java.util.TreeSet

class ListSearchParameterProvider : PreviewParameterProvider<ListSearchUiState> {
    override val values = sequenceOf(
        ListSearchUiState(
            place = PlaceModelParameterProvider().values.first(),
            area = CityParameterProvider().values.elementAt(0) as AreaModel,
            sigungu = CityParameterProvider().values.elementAt(1) as City.SigunguModel,
            options = ListOptionStatus(
                categoryStatus = CategoryStatus.PLACE,
                page = 0,
                detailFilter = TreeSet(setOf(1,2, 3, 6, 7)),
                areaCode = "2",
                sigunguCode = "1",
                arrange = "A"
            ),
            isLastPage = false
        )
    )
}