package kr.techit.lion.presentation.compose.screen.search.list

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kr.techit.lion.designsystem.component.ProgressBarScreen
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.common.ObserveAsEvents
import kr.techit.lion.presentation.compose.screen.search.PlaceRow
import kr.techit.lion.presentation.compose.screen.search.component.CityExposedDropdownMenu
import kr.techit.lion.presentation.compose.screen.search.component.DisabilityOption
import kr.techit.lion.presentation.compose.screen.search.list.model.City.AreaModel
import kr.techit.lion.presentation.compose.screen.search.list.model.City.SigunguModel
import kr.techit.lion.presentation.compose.screen.search.list.model.PlaceModel
import kr.techit.lion.presentation.compose.screen.search.list.model.ListSearchUiState
import kr.techit.lion.presentation.compose.screen.search.list.preview.ListSearchParameterProvider
import kr.techit.lion.presentation.compose.screen.search.list.vm.ListSearchUiAction
import kr.techit.lion.presentation.compose.screen.search.list.vm.ListSearchUiEvent
import kr.techit.lion.presentation.compose.screen.search.list.vm.SearchListViewModel
import kr.techit.lion.presentation.compose.screen.search.model.CategoryStatus
import kr.techit.lion.presentation.compose.screen.search.model.Disability
import kr.techit.lion.presentation.delegate.NetworkEvent
import java.util.TreeSet
import kotlin.coroutines.CoroutineContext

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

    val uiState = viewModel.uiState.collectAsState(Dispatchers.Main.immediate)
    val uiState2 = viewModel.uiState.collectAsStateWithLifecycle(Dispatchers.Main.immediate)

    var showProgressBar by remember { mutableStateOf(false) }

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is ListSearchUiEvent.ShowBottomSheet -> showBottomSheet(event.disability)
        }
    }

    ObserveAsEvents(viewModel.networkEvent) { event ->
        when (event) {
            is NetworkEvent.Loading -> showProgressBar = true
            is NetworkEvent.Success -> showProgressBar = false
            is NetworkEvent.Error -> showProgressBar = false
        }
    }

    LaunchedEffect(selectedCategory) {
        viewModel.updateTabStatus(selectedCategory)
    }

    LaunchedEffect(bottomSheetOption) {
        viewModel.updateBottomSheetOption(bottomSheetOption)
    }

    ListSearchScreen(
        selectedPhysicalDisabilityOptionCount = selectedPhysicalDisabilityOptionCount,
        selectedVisualDisabilityOptionCount = selectedVisualDisabilityOptionCount,
        selectedHearingDisabilityOptionCount = selectedHearingDisabilityOptionCount,
        selectedInfantDisabilityOptionCount = selectedInfantDisabilityOptionCount,
        selectedElderlyDisabilityOptionCount = selectedElderlyDisabilityOptionCount,
        showProgressBar = showProgressBar,
        places = uiState.value.place,
        placeColumnSize = uiState.value.placeColumnSize,
        area = uiState.value.area,
        sigungu = uiState.value.sigungu,
        onAction = { action -> viewModel.onListSearchAction(action) },
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
    showProgressBar: Boolean,
    places: List<PlaceModel>,
    placeColumnSize: Int,
    area: AreaModel,
    sigungu: SigunguModel,
    onAction: (ListSearchUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    ProgressBarScreen(
        showProgressBar = showProgressBar,
        content ={
            LazyColumn(modifier = modifier.padding(20.dp)) {
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
                        onClickDisabilityButton = {
                            onAction(ListSearchUiAction.OnClickDisabilityOptionButton(it))
                        }
                    )
                }

                item {
                    CityExposedDropdownMenu(
                        area = area,
                        sigungu = sigungu,
                        areaPlaceHolder = stringResource(R.string.text_nationwide),
                        sigunguPlaceHolder = stringResource(R.string.text_sigungu_placeholder),
                        onSelectArea = { onAction(ListSearchUiAction.OnSelectArea(it)) },
                        onSelectSigungu = { onAction(ListSearchUiAction.OnSelectSigungu(it)) }
                    )
                }

                item {
                    PlaceRow(places, placeColumnSize)
                }
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun ListSearchScreenPreview(
    @PreviewParameter(ListSearchParameterProvider ::class)
    items: ListSearchUiState,
) {
    ListSearchScreen(
        selectedPhysicalDisabilityOptionCount = 0,
        selectedVisualDisabilityOptionCount = 0,
        selectedHearingDisabilityOptionCount = 2,
        selectedInfantDisabilityOptionCount = 0,
        selectedElderlyDisabilityOptionCount = 3,
        showProgressBar = true,
        places = items.place,
        placeColumnSize = items.placeColumnSize,
        onAction = {},
        area = items.area,
        sigungu = items.sigungu,
    )
}
