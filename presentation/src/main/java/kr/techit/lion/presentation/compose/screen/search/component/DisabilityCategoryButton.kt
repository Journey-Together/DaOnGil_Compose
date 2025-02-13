package kr.techit.lion.presentation.compose.screen.search.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.ext.clickableWithCustomRippleEffect
import kr.techit.lion.presentation.compose.screen.search.model.Disability

@Composable
fun DisabilityCategoryButton(
    disability: Disability,
    selectedOptionCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val imageResourceId = if (selectedOptionCount > 0) {
        disability.selectedImageResourceId
    } else {
        disability.unSelectedImageResourceId
    }

    val title = if (selectedOptionCount > 0) {
        "${stringResource(disability.contentDescriptionResourceId)}($selectedOptionCount)"
    } else {
        stringResource(disability.contentDescriptionResourceId)
    }

    val titleColorResourceId = if (selectedOptionCount > 0) {
        R.color.search_view_main
    } else {
        R.color.search_view_category_name
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickableWithCustomRippleEffect {
            onClick()
        }
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = imageResourceId),
            contentDescription = stringResource(id = disability.contentDescriptionResourceId),
            modifier = Modifier
                .clip(CircleShape)
        )

        Text(
            text = title,
            color = colorResource(id = titleColorResourceId)
        )
    }
}

@Composable
@Preview
fun DisabilityCategoryButtonPreview() {
    DisabilityCategoryButton(
        onClick = {},
        disability = Disability.PHYSICAL_DISABILITY,
        selectedOptionCount = 2
    )
}


