package kr.techit.lion.presentation.compose.screen.search.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.screen.search.model.ScreenStatus

@Composable
fun ScreenChangeButton(
    onClick: (ScreenStatus) -> Unit,
    status: ScreenStatus,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(color = colorResource(R.color.floating_action_button))
            .clickable { onClick(status) }
            .padding(10.dp)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(status.iconResourceId),
            contentDescription = stringResource(status.contentDescriptionResourceId),
            modifier = Modifier
                .align(Alignment.Center)
                .size(25.dp)
        )
    }
}

@Composable
@Preview
fun ScreenChangeButtonPreview() {
    ScreenChangeButton(
        status = ScreenStatus.List,
        onClick = {}
    )
}