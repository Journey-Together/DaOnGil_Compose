package kr.techit.lion.presentation.compose.screen.search.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability

@Composable
fun DisabilityOptionChip(
    item: FacilitiesForPersonWithDisability,
    selected: Boolean,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val title = stringResource(id = item.contentDescriptionResourceId)

    val selectedColor = colorResource(id = R.color.detail_category_selected_color)
    val unselectedColor = colorResource(id = R.color.detail_category_unselected_color)
    val mainColor = if (selected) selectedColor else unselectedColor

    FilterChip(
        onClick = {
            onClick(selected)
        },
        modifier = modifier,
        border = FilterChipDefaults.filterChipBorder(
            borderWidth = 1.dp,
            enabled = true,
            selected = selected,
            borderColor = mainColor,
            selectedBorderWidth = 2.dp,
            selectedBorderColor = selectedColor
        ),
        selected = selected,
        label = @Composable {
            Text(
                text = title,
                color = mainColor
            )
        },
        leadingIcon = @Composable {
            Icon(
                imageVector = ImageVector.vectorResource(id = item.iconResourceId),
                contentDescription = title,
                modifier = Modifier.size(20.dp),
                tint = mainColor
            )
        }
    )
}

@Composable
@Preview
fun DisabilityOptionChipPreview() {
    DisabilityOptionChip(
        item = FacilitiesForPersonWithDisability.PARKING,
        selected = false,
        onClick = {}
    )
}