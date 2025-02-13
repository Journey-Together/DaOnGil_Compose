package kr.techit.lion.designsystem.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import kr.techit.lion.designsystem.R

@Composable
fun DaongilProgressBar(
    durationMillis: Int,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing)
        )
    )

    Image(
        imageVector = ImageVector.vectorResource(R.drawable.progress_icon),
        contentDescription = null,
        modifier = modifier.graphicsLayer(rotationZ = angle)
    )
}

@Composable
@Preview
fun DaongilProgressBarPreview(){
    DaongilProgressBar(durationMillis = 3000)
}