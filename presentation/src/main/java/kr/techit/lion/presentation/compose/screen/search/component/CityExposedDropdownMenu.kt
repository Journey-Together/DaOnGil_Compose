package kr.techit.lion.presentation.compose.screen.search.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kr.techit.lion.presentation.compose.screen.search.list.model.City.SigunguModel
import kr.techit.lion.presentation.compose.screen.search.list.model.City.AreaModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityExposedDropdownMenu(
    area: AreaModel,
    sigungu: SigunguModel,
    areaPlaceHolder: String,
    sigunguPlaceHolder: String,
    onSelectArea: (name: String) -> Unit,
    onSelectSigungu: (name: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var areaText by remember { mutableStateOf(areaPlaceHolder) }
    var sigunguText by remember { mutableStateOf(sigunguPlaceHolder) }

    DropdownMenu(
        options = area.names,
        selectedText = areaText,
        onSelectOption = { selected ->
            areaText = selected
            sigunguText = sigunguPlaceHolder
            onSelectArea(selected)
        },
        modifier = modifier
    )

    if (sigungu.names.isNotEmpty()) {
        DropdownMenu(
            options = sigungu.names,
            selectedText = sigunguText,
            onSelectOption = { selected ->
                sigunguText = selected
                onSelectSigungu(selected)
            },
            modifier = modifier
        )
    }
}

@Composable
@Preview
fun AreaExposedDropdownMenuPreview() {
    CityExposedDropdownMenu(
        area = AreaModel(names = listOf("서울", "경기", "인천")),
        sigungu = SigunguModel(names = listOf("수원", "성남", "안양")),
        areaPlaceHolder = "전국",
        sigunguPlaceHolder = "시/군/구",
        onSelectArea = {name -> },
        onSelectSigungu = {name -> }
    )
}