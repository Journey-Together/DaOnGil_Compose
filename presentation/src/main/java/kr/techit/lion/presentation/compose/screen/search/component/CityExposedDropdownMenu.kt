package kr.techit.lion.presentation.compose.screen.search.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import kr.techit.lion.presentation.compose.screen.search.list.model.City
import kr.techit.lion.presentation.compose.screen.search.list.model.City.SigunguModel
import kr.techit.lion.presentation.compose.screen.search.list.model.City.AreaModel
import kr.techit.lion.presentation.compose.screen.search.list.preview.CityParameterProvider

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
fun AreaExposedDropdownMenuPreview(
    @PreviewParameter(CityParameterProvider::class)
    items: City
) {
    when (items) {
        is AreaModel -> {
            CityExposedDropdownMenu(
                area = items,
                sigungu = SigunguModel(names = emptyList()),
                areaPlaceHolder = "전국",
                sigunguPlaceHolder = "시/군/구",
                onSelectArea = { name -> },
                onSelectSigungu = { name -> }
            )
        }

        is SigunguModel -> {
            CityExposedDropdownMenu(
                area = AreaModel(names = emptyList()),
                sigungu = items,
                areaPlaceHolder = "전국",
                sigunguPlaceHolder = "시/군/구",
                onSelectArea = { name -> },
                onSelectSigungu = { name -> }
            )
        }
    }
}