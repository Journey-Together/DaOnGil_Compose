package kr.techit.lion.presentation.compose.screen.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.screen.search.component.DisabilityOptionChip
import kr.techit.lion.presentation.compose.screen.search.component.OptionApplyButton
import kr.techit.lion.presentation.compose.screen.search.component.ResetButton
import kr.techit.lion.presentation.compose.screen.search.component.ScreenChangeButton
import kr.techit.lion.presentation.compose.screen.search.component.SearchTab
import kr.techit.lion.presentation.compose.screen.search.component.SearchTopBar
import kr.techit.lion.presentation.compose.screen.search.list.ListSearchScreen
import kr.techit.lion.presentation.compose.screen.search.map.MapSearchScreen
import kr.techit.lion.presentation.compose.screen.search.model.CategoryStatus
import kr.techit.lion.presentation.compose.screen.search.model.Disability
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability
import kr.techit.lion.presentation.compose.screen.search.model.ScreenStatus
import kr.techit.lion.presentation.compose.screen.search.vm.BottomSheetViewModel
import java.util.TreeSet

@Composable
fun SearchPlaceMainScreen(
    viewModel: BottomSheetViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SearchPlaceMainScreen(
        selectedPhysicalDisabilityOptionCount = uiState.physicalOption.size,
        selectedVisualDisabilityOptionCount = uiState.visualOption.size,
        selectedHearingDisabilityOptionCount = uiState.hearingOption.size,
        selectedInfantDisabilityOptionCount = uiState.infantOption.size,
        selectedElderlyDisabilityOptionCount = uiState.elderlyOption.size,
        bottomSheetOption = uiState.bottomSheetOptions,
        currentSelectedOption = uiState.currentSelectedOption,
        allSelectedOption = uiState.selectedDisabilityOptions,
        onClickDisabilityButton = viewModel::onClickDisability,
        onClickOption = viewModel::modifyCurrentSelectedOption,
        onClickAllOption = viewModel::addAllOption,
        onClickApply = viewModel::modifySelectedOption,
        clearOption = viewModel::clearCurrentSelectedOption
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchPlaceMainScreen(
    bottomSheetOption: List<FacilitiesForPersonWithDisability>,
    selectedPhysicalDisabilityOptionCount: Int,
    selectedVisualDisabilityOptionCount: Int,
    selectedHearingDisabilityOptionCount: Int,
    selectedInfantDisabilityOptionCount: Int,
    selectedElderlyDisabilityOptionCount: Int,
    currentSelectedOption: TreeSet<Int>,
    allSelectedOption: TreeSet<Int>,
    onClickDisabilityButton: (Disability) -> Unit,
    onClickOption: (Int) -> Unit,
    onClickAllOption: () -> Unit,
    onClickApply: () -> Unit,
    clearOption: () -> Unit
) {
    var screenStatus by remember { mutableStateOf<ScreenStatus>(ScreenStatus.List) }
    var categoryStatus by remember { mutableStateOf<CategoryStatus>(CategoryStatus.PLACE) }

    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = { SearchTopBar() },
        floatingActionButton = {
            ScreenChangeButton(
                status = screenStatus,
                onClick = { status ->
                    screenStatus = when (status) {
                        is ScreenStatus.List -> ScreenStatus.Map
                        is ScreenStatus.Map -> ScreenStatus.List
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.EndOverlay
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            SearchTab(
                onSelectTab = { status ->
                    categoryStatus = status
                }
            )
            when (screenStatus) {
                is ScreenStatus.List -> {
                    ListSearchScreen(
                        selectedPhysicalDisabilityOptionCount = selectedPhysicalDisabilityOptionCount,
                        selectedVisualDisabilityOptionCount = selectedVisualDisabilityOptionCount,
                        selectedHearingDisabilityOptionCount = selectedHearingDisabilityOptionCount,
                        selectedInfantDisabilityOptionCount = selectedInfantDisabilityOptionCount,
                        selectedElderlyDisabilityOptionCount = selectedElderlyDisabilityOptionCount,
                        selectedCategory = categoryStatus,
                        showBottomSheet = { disability ->
                            showBottomSheet = true
                            onClickDisabilityButton(disability)
                        },
                        bottomSheetOption = allSelectedOption
                    )
                }

                is ScreenStatus.Map -> MapSearchScreen()
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                        clearOption()
                    },
                    sheetState = bottomSheetState,
                    dragHandle = {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.bottom_sheet_head),
                            contentDescription = null,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    },
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.text_detail_category),
                        )

                        FilterChip(
                            onClick = { onClickAllOption() },
                            modifier = Modifier.padding(4.dp),
                            selected = false,
                            label = @Composable {
                                Text(
                                    text = stringResource(R.string.text_all),
                                    color = colorResource(id = R.color.detail_category_chip_state_color)
                                )
                            }
                        )

                        HorizontalDivider(modifier = Modifier.fillMaxWidth())

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(top = 10.dp)
                        ) {
                            bottomSheetOption.forEach { item ->
                                val isOptionSelected = allSelectedOption.contains(item.code)
                                        || currentSelectedOption.contains(item.code)
                                DisabilityOptionChip(
                                    item = item,
                                    selected = isOptionSelected,
                                    onClick = { selected ->
                                        onClickOption(item.code)
                                    }
                                )
                            }
                        }
                    }

                    Row {
                        ResetButton(
                            onClick = { clearOption() },
                            modifier = Modifier.weight(0.4f)
                        )

                        OptionApplyButton(
                            onClick = {
                                showBottomSheet = false
                                onClickApply()
                            },
                            modifier = Modifier.weight(0.6f)
                        )
                    }
                }
            }
            HorizontalDivider(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(color = colorResource(R.color.primary_disabled))
            )
        }
    }
}

@Composable
@Preview
fun SearchPlaceMainScreenPreview() {
    SearchPlaceMainScreen(
        selectedPhysicalDisabilityOptionCount = 0,
        selectedVisualDisabilityOptionCount = 0,
        selectedHearingDisabilityOptionCount = 0,
        selectedInfantDisabilityOptionCount = 0,
        selectedElderlyDisabilityOptionCount = 0,
        bottomSheetOption = emptyList<FacilitiesForPersonWithDisability>(),
        allSelectedOption = TreeSet(setOf<Int>()),
        currentSelectedOption = TreeSet(setOf<Int>()),
        onClickAllOption = {},
        onClickDisabilityButton = {},
        onClickOption = {},
        onClickApply = {},
        clearOption = {}
    )
}