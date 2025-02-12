package kr.techit.lion.presentation.compose.screen.intro.concern

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.launch
import kr.techit.lion.designsystem.component.ProgressBarScreen
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.common.ObserveAsEvents
import kr.techit.lion.presentation.compose.ext.clickableWithCustomRippleEffect
import kr.techit.lion.presentation.compose.screen.intro.concern.vm.ConcernUiAction
import kr.techit.lion.presentation.compose.screen.intro.concern.vm.ConcernUiEvent
import kr.techit.lion.presentation.compose.screen.intro.concern.vm.ConcernViewModel
import kr.techit.lion.presentation.compose.screen.intro.concern.vm.model.Disability
import kr.techit.lion.presentation.delegate.NetworkEvent

@Composable
fun ConcernScreen(
    navigateToMain: () -> Unit,
    viewModel: ConcernViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showProgressBar by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    ObserveAsEvents(viewModel.networkEvent) { event ->
        when (event) {
            NetworkEvent.Loading -> showProgressBar = true
            NetworkEvent.Success -> {
                showProgressBar = false
                navigateToMain()
            }

            is NetworkEvent.Error -> {
                showProgressBar = false
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(message = event.msg)
                }
            }
        }
    }

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is ConcernUiEvent.NavigateToMain -> navigateToMain()
        }
    }

    ConcernScreen(
        showProgressBar,
        snackBarHostState,
        selectedConcernType = uiState.selectedConcernType,
        onAction = { viewModel.onConcernUiAction(it) },
        clickSubmitButton = { viewModel.onClickSubmitButton() }
    )
}

@Composable
fun ConcernScreen(
    showProgressBar: Boolean,
    snackBarHostState: SnackbarHostState,
    selectedConcernType: ImmutableSet<Disability>,
    onAction: (ConcernUiAction) -> Unit,
    clickSubmitButton: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    ProgressBarScreen(
        showProgressBar = showProgressBar,
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = dimensionResource(R.dimen.margin_basic))
                ) {
                    Text(
                        text = stringResource(R.string.text_plz_select_concern_keyword)
                    )

                    Text(
                        text = stringResource(R.string.text_can_change_concern_keyword)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val resources = ConcernResource.entries
                        items(resources.size) { idx ->
                            val item = resources[idx]
                            val isSelected = selectedConcernType.contains(item.type)
                            ConcernButton(
                                resource = item,
                                isSelected = isSelected,
                                concernAction = { onAction(it) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            if (selectedConcernType.isEmpty()) {
                                coroutineScope.launch {
                                    snackBarHostState.showSnackbar(
                                        message = context.getString(R.string.plz_select_interest_type)
                                    )
                                }
                            } else {
                                clickSubmitButton()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickableWithCustomRippleEffect { selectedConcernType.isNotEmpty() }
                    ) {
                        Text("완료")
                    }

                    SnackbarHost(hostState = snackBarHostState)
                }
            }
        }
    )
}

@Composable
fun ConcernButton(
    resource: ConcernResource,
    isSelected: Boolean,
    concernAction: (ConcernUiAction) -> Unit
) {
    Image(
        modifier = Modifier
            .clickableWithCustomRippleEffect {
                concernAction(ConcernUiAction.OnClickConcernButton(resource.type))
            }
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp)),
        imageVector = ImageVector.vectorResource(
            id = if (isSelected) resource.selectedResourceId else resource.unselectedResourceId
        ),
        contentDescription = stringResource(resource.contentDescriptionId)
    )
}

@Composable
@Preview(showBackground = true)
fun ConcernScreenPreview() {
    ConcernScreen(
        showProgressBar = true,
        snackBarHostState = SnackbarHostState(),
        selectedConcernType = persistentSetOf(),
        onAction = {},
        clickSubmitButton = {}
    )
}