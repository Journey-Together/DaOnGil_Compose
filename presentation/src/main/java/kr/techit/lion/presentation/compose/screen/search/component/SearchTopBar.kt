package kr.techit.lion.presentation.compose.screen.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import kr.techit.lion.presentation.R

@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(10.dp)
            .clip(CircleShape)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = colorResource(R.color.search_view_background))
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_search),
            contentDescription = stringResource(R.string.hint_search),
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp)
        )

        Text(
            text = stringResource(R.string.hint_search),
            color = colorResource(R.color.search_view_category_name),
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SearchTopBarPreview(){
    SearchTopBar()
}