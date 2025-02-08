package kr.techit.lion.presentation.compose.screen.search.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kr.techit.lion.presentation.compose.screen.search.model.CategoryStatus
import kr.techit.lion.presentation.R

@Composable
fun SearchTab(
    onSelectTab: (CategoryStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    TabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = Color.Transparent,
        modifier = modifier.fillMaxWidth(),
        indicator = { tabPositions ->
            TabRowDefaults.PrimaryIndicator(
                Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .height(2.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = Color(0xFFE53935)
            )
        },
    ) {
        enumValues<CategoryStatus>().forEachIndexed { index, value ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    onSelectTab(value)
                },
                text = {
                    Text(
                        text = stringResource(value.titleResourceId),
                        color = if (selectedTabIndex == index) Color(0xFFE53935) else Color.Gray,
                    )
                },
            )
        }
    }
}

@Composable
@Preview
fun SearchTabPreview() {
    SearchTab(
        onSelectTab = {}
    )
}