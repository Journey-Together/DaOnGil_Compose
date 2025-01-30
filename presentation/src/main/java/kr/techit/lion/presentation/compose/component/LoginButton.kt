package kr.techit.lion.presentation.compose.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.techit.lion.presentation.R

@Composable
fun LoginButton(
    action: () -> Unit,
    @DrawableRes resourceId: Int,
    modifier: Modifier = Modifier
) {

    Image(
        painter = painterResource(id = resourceId),
        contentDescription = stringResource(R.string.text_kakao_login),
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp)
            .clickable(onClick = action)
            .padding(bottom = dimensionResource(id = R.dimen.margin_big3))
    )
}

@Composable
@Preview
fun LoginButtonPreview() {
    LoginButton(
        action = {},
        resourceId = R.drawable.kakao_login,
    )
}