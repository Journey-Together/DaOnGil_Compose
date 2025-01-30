package kr.techit.lion.presentation.compose.ext

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

sealed interface ClickEffect {
    data object RemoveRippleEffect : ClickEffect
    data class UseRippleEffect(val ripple: Indication) : ClickEffect
}

inline fun Modifier.clickableWithCustomRippleEffect(
    clickEffect: ClickEffect = ClickEffect.RemoveRippleEffect,
    crossinline onClick: () -> Unit
): Modifier = composed {
    val indication = when (clickEffect) {
        is ClickEffect.RemoveRippleEffect -> null
        is ClickEffect.UseRippleEffect -> clickEffect.ripple
    }
    clickable(
        indication = indication,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}