package kr.techit.lion.presentation.compose.screen.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.techit.lion.presentation.compose.screen.search.model.Disability

@Composable
fun DisabilityOption(
    selectedPhysicalDisabilityOptionCount: Int,
    selectedVisualDisabilityOptionCount: Int,
    selectedHearingDisabilityOptionCount: Int,
    selectedInfantDisabilityOptionCount: Int,
    selectedElderlyDisabilityOptionCount: Int,
    onClickDisabilityButton: (Disability) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 30.dp,
                alignment = Alignment.CenterHorizontally
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            DisabilityCategoryButton(
                disability = Disability.HEARING_IMPAIRMENT,
                selectedOptionCount = selectedHearingDisabilityOptionCount,
                onClick = {
                    onClickDisabilityButton(Disability.HEARING_IMPAIRMENT)
                }
            )
            DisabilityCategoryButton(
                disability = Disability.ELDERLY_PEOPLE,
                selectedOptionCount = selectedElderlyDisabilityOptionCount,
                onClick = {
                    onClickDisabilityButton(Disability.ELDERLY_PEOPLE)
                }
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 20.dp,
                alignment = Alignment.CenterHorizontally
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            DisabilityCategoryButton(
                disability = Disability.VISUAL_IMPAIRMENT,
                selectedOptionCount = selectedVisualDisabilityOptionCount,
                onClick = {
                    onClickDisabilityButton(Disability.VISUAL_IMPAIRMENT)
                }
            )
            DisabilityCategoryButton(
                disability = Disability.INFANT_FAMILY,
                selectedOptionCount = selectedInfantDisabilityOptionCount,
                onClick = {
                    onClickDisabilityButton(Disability.INFANT_FAMILY)
                }

            )
            DisabilityCategoryButton(
                disability = Disability.PHYSICAL_DISABILITY,
                selectedOptionCount = selectedPhysicalDisabilityOptionCount,
                onClick = {
                    onClickDisabilityButton(Disability.PHYSICAL_DISABILITY)
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DisabilityOptionPreview() {
    DisabilityOption(
        onClickDisabilityButton = {},
        selectedPhysicalDisabilityOptionCount = 0,
        selectedVisualDisabilityOptionCount = 0,
        selectedHearingDisabilityOptionCount = 2,
        selectedInfantDisabilityOptionCount = 0,
        selectedElderlyDisabilityOptionCount = 0,
    )
}