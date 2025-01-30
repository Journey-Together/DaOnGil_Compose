package kr.techit.lion.presentation.compose.screen.concern

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.screen.concern.model.ConcernResource
import kr.techit.lion.presentation.compose.screen.concern.vm.InterestViewModel
import kr.techit.lion.presentation.delegate.NetworkEvent

@Composable
fun ConcernScreen(
    navigateToMain: () -> Unit,
    viewModel: InterestViewModel = hiltViewModel()
) {
    val selectedConcernType by viewModel.selectedType.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.networkEvent) {
        viewModel.networkEvent.collect { event ->
            when(event) {
                NetworkEvent.Loading -> Unit
                NetworkEvent.Success -> navigateToMain()
                is NetworkEvent.Error -> {
                    snackBarHostState.showSnackbar(
                        message = event.msg
                    )
                }
            }
        }
    }

    ConcernScreen(
        snackBarHostState,
        selectedConcernType,
        clickConcernButton = { type ->
            viewModel.onSelectInterest(type)
        },
        clickSubmitButton = {
            viewModel.onClickSubmitButton()
        }
    )
}

@Composable
fun ConcernScreen(
    snackBarHostState: SnackbarHostState,
    selectedConcernType: List<ConcernResource>,
    clickConcernButton: (ConcernResource) -> Unit,
    clickSubmitButton: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
            items(ConcernResource.entries.size) { idx ->
                val concernType = ConcernResource.entries[idx]
                val isSelected = selectedConcernType.contains(concernType)
                ConcernButton(
                    type = concernType,
                    isSelected = isSelected,
                    onClick = { type ->
                        clickConcernButton(type)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        SnackbarHost(
            hostState = snackBarHostState
        )

        Button(
            onClick = {
                if (selectedConcernType.isEmpty()) {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.plz_select_interest_type)
                        )
                    }
                }else{
                    clickSubmitButton()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { selectedConcernType.isNotEmpty() }
        ) {
            Text("완료")
        }
    }
}

@Composable
fun ConcernButton(
    type: ConcernResource,
    isSelected: Boolean,
    onClick: (ConcernResource) -> Unit
) {
    Image(
        modifier = Modifier
            .clickable { onClick(type) }
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp)),
        imageVector = ImageVector.vectorResource(
            id = if (isSelected) type.selectedResourceId else type.unselectedResourceId
        ),
        contentDescription = stringResource(type.contentDescriptionId)
    )
}

@Composable
@Preview(showBackground = true)
fun ConcernScreenPreview() {
    ConcernScreen(
        snackBarHostState = remember { SnackbarHostState() },
        selectedConcernType = emptyList(),
        clickConcernButton = {},
        clickSubmitButton = {}
    )
}