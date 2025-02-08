package kr.techit.lion.presentation.compose.screen.search.model

import androidx.annotation.StringRes
import kr.techit.lion.presentation.R

enum class CategoryStatus(@StringRes val titleResourceId: Int) {
    PLACE(R.string.tourist_spot),
    RESTAURANT(R.string.restaurant),
    LODGING(R.string.lodging)
}