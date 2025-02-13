package kr.techit.lion.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kr.techit.lion.designsystem.R

@Composable
fun PlaceHigh(
    placeName: String,
    placeAddr: String,
    placeId: Long,
    placeImgPath: String,
    disabilityCode: List<String>,
    onClickPlace: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .clickable{ onClickPlace(placeId) }
            .fillMaxWidth()
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(2.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(placeImgPath)
                        .build(),
                    contentDescription = placeName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .align(Alignment.BottomStart)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                                startY = 70f
                            )
                        )
                )


                Row(
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(
                            start = dimensionResource(id = R.dimen.margin_small1),
                            bottom = dimensionResource(id = R.dimen.margin_small1)
                        )
                        .wrapContentWidth()
                ) {
                    disabilityCode.forEach {
                        val (imageResourceId, contentDescriptionId) = when (it) {
                            "1" -> Pair(
                                R.drawable.physical_disability_radius_icon,
                                R.string.text_physical_disability
                            )

                            "2" -> Pair(
                                R.drawable.visual_impairment_radius_icon,
                                R.string.text_visual_impairment
                            )

                            "3" -> Pair(
                                R.drawable.hearing_impairment_radius_icon,
                                R.string.text_hearing_impairment
                            )

                            "4" -> Pair(
                                R.drawable.infant_familly_radius_icon,
                                R.string.text_infant_family
                            )

                            else -> Pair(
                                R.drawable.elderly_people_radius_icon,
                                R.string.text_elderly_person
                            )
                        }

                        Image(
                            imageVector = ImageVector.vectorResource(id = imageResourceId),
                            contentDescription = stringResource(id = contentDescriptionId),
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .background(Color(0x99FFFFFF))
        ) {
            Text(
                text = placeName,
                modifier = Modifier
                    .wrapContentSize()
                    .fillMaxWidth()
            )

            Text(
                text = placeAddr,
                maxLines = 2,
                overflow = TextOverflow.Visible,
                modifier = Modifier
                    .wrapContentSize()
                    .fillMaxWidth()
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PlaceHighPreview() {
    PlaceHigh(
        placeName = "소도야영장",
        placeAddr = "강원특별자치도 태백시 천제단길 181 (소도동)",
        placeId = 3344099,
        placeImgPath = "http://tong.visitkorea.or.kr/cms/resource/87/3344087_image2_1.jpg",
        disabilityCode = listOf("1", "2"),
        onClickPlace = {}
    )
}