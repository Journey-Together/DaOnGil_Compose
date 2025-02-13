package kr.techit.lion.presentation.compose.screen.search.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.techit.lion.presentation.R

@Composable
fun OptionApplyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(end = 20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.search_view_main)),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.text_apply),
            color = Color.White
        )
    }
}

@Composable
@Preview
fun OptionApplyButtonPreview() {
    OptionApplyButton(
        onClick = {}
    )
}