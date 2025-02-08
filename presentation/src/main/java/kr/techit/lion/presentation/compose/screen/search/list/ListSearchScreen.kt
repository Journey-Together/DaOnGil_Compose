package kr.techit.lion.presentation.compose.screen.search.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.screen.search.component.CityExposedDropdownMenu
import kr.techit.lion.presentation.compose.screen.search.component.DisabilityOption
import kr.techit.lion.presentation.compose.screen.search.list.model.City.AreaModel
import kr.techit.lion.presentation.compose.screen.search.list.model.City.SigunguModel
import kr.techit.lion.presentation.compose.screen.search.list.model.ListSearchUIModel
import kr.techit.lion.presentation.compose.screen.search.list.model.ListSearchUIModel.PlaceModel
import kr.techit.lion.presentation.compose.screen.search.list.vm.SearchListViewModel
import kr.techit.lion.presentation.compose.screen.search.model.CategoryStatus
import kr.techit.lion.presentation.compose.screen.search.model.Disability
import java.util.TreeSet

@Composable
fun ListSearchScreen(
    selectedPhysicalDisabilityOptionCount: Int,
    selectedVisualDisabilityOptionCount: Int,
    selectedHearingDisabilityOptionCount: Int,
    selectedInfantDisabilityOptionCount: Int,
    selectedElderlyDisabilityOptionCount: Int,
    selectedCategory: CategoryStatus,
    showBottomSheet: (Disability) -> Unit,
    bottomSheetOption: TreeSet<Int>,
    modifier: Modifier = Modifier,
    viewModel: SearchListViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(selectedCategory) {
        viewModel.updateTabStatus(selectedCategory)
    }

    LaunchedEffect(bottomSheetOption) {
        viewModel.updateBottomSheetOption(bottomSheetOption)
    }

    LaunchedEffect(Unit) {
        viewModel.loadAreaModel()
    }

    ListSearchScreen(
        selectedPhysicalDisabilityOptionCount = selectedPhysicalDisabilityOptionCount,
        selectedVisualDisabilityOptionCount = selectedVisualDisabilityOptionCount,
        selectedHearingDisabilityOptionCount = selectedHearingDisabilityOptionCount,
        selectedInfantDisabilityOptionCount = selectedInfantDisabilityOptionCount,
        selectedElderlyDisabilityOptionCount = selectedElderlyDisabilityOptionCount,
        place = uiState.value.place,
        area = uiState.value.area,
        sigungu = uiState.value.sigungu,
        onClickDisability = { showBottomSheet(it) },
        onSelectArea = viewModel::loadSigunguModel,
        onSelectSigungu = viewModel::onSelectSigungu,
        modifier = modifier
    )
}

@Composable
fun ListSearchScreen(
    selectedPhysicalDisabilityOptionCount: Int,
    selectedVisualDisabilityOptionCount: Int,
    selectedHearingDisabilityOptionCount: Int,
    selectedInfantDisabilityOptionCount: Int,
    selectedElderlyDisabilityOptionCount: Int,
    place: List<PlaceModel>,
    area: AreaModel,
    sigungu: SigunguModel,
    onClickDisability: (Disability) -> Unit,
    onSelectArea: (areaName: String) -> Unit,
    onSelectSigungu: (sigunguName: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(20.dp)
    ) {
        item {
            Text(
                text = buildAnnotatedString {
                    append("${stringResource(R.string.text_search_guide1)}\n")
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(R.color.search_view_main)
                        )
                    ) {
                        append(stringResource(R.string.text_category))
                    }
                    append(stringResource(R.string.text_search_guide2))
                }
            )
        }

        item {
            DisabilityOption(
                selectedPhysicalDisabilityOptionCount,
                selectedVisualDisabilityOptionCount,
                selectedHearingDisabilityOptionCount,
                selectedInfantDisabilityOptionCount,
                selectedElderlyDisabilityOptionCount,
                onClickDisabilityButton = { disability ->
                    onClickDisability(disability)
                }
            )
        }

        item {
            CityExposedDropdownMenu(
                area = area,
                sigungu = sigungu,
                areaPlaceHolder = stringResource(R.string.text_nationwide),
                sigunguPlaceHolder = stringResource(R.string.text_sigungu_placeholder),
                onSelectArea = { onSelectArea(it) },
                onSelectSigungu = { onSelectSigungu(it) }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ListSearchScreenPreview() {
    ListSearchScreen(
        selectedPhysicalDisabilityOptionCount = 0,
        selectedVisualDisabilityOptionCount = 0,
        selectedHearingDisabilityOptionCount = 2,
        selectedInfantDisabilityOptionCount = 0,
        selectedElderlyDisabilityOptionCount = 3,
        place = emptyList(),
        area = AreaModel(persistentListOf()),
        sigungu = SigunguModel(persistentListOf()),
        onClickDisability = {},
        onSelectArea = {},
        onSelectSigungu = {}
    )
}
