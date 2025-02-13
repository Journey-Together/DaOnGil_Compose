package kr.techit.lion.presentation.compose.screen.search.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kr.techit.lion.presentation.R

sealed class ScreenStatus(
    @DrawableRes val iconResourceId: Int,
    @StringRes val contentDescriptionResourceId: Int
) {
    data object List : ScreenStatus(
        iconResourceId = R.drawable.list_icon,
        contentDescriptionResourceId = R.string.watching_list
    )

    data object Map : ScreenStatus(
        iconResourceId = R.drawable.map_icon,
        contentDescriptionResourceId = R.string.watching_map
    )
}