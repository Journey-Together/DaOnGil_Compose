package kr.techit.lion.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kr.techit.lion.designsystem.component.DaongilProgressBar

@Composable
fun ProgressBarScreen(
    showProgressBar: Boolean,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        content()
        if (showProgressBar) {
            DaongilProgressBar(
                durationMillis = 3000,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}