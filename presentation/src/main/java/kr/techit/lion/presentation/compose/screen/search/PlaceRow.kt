package kr.techit.lion.presentation.compose.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import kr.techit.lion.designsystem.component.PlaceHigh
import kr.techit.lion.presentation.compose.screen.search.list.model.PlaceModel
import kr.techit.lion.presentation.compose.screen.search.list.preview.PlaceModelParameterProvider

@Composable
fun PlaceRow(
    place: List<PlaceModel>,
    placeColumnSize: Int,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
    ) {
        repeat(placeColumnSize) { rowIndex ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val start = rowIndex * 2
                val end = minOf(start + 2, place.size)

                place.subList(start, end).forEach { placeItem ->
                    PlaceHigh(
                        placeName = placeItem.placeName,
                        placeAddr = placeItem.placeAddr,
                        placeId = placeItem.placeId,
                        placeImgPath = placeItem.placeImg,
                        disabilityCode = placeItem.disability,
                        onClickPlace = {},
                        modifier = Modifier.weight(0.5f),
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PlaceRowPreview(
    @PreviewParameter(PlaceModelParameterProvider::class)
    place: List<PlaceModel>
) {
    PlaceRow(
        place,
        placeColumnSize = 5
    )
}