package kr.techit.lion.presentation.compose.screen.concern.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kr.techit.lion.presentation.R

enum class ConcernResource(
    @DrawableRes val selectedResourceId: Int,
    @DrawableRes val unselectedResourceId: Int,
    @StringRes val contentDescriptionId: Int
) {
    Physical(
        R.drawable.physical_select,
        R.drawable.physical_no_select,
        R.string.text_physical_disability
    ),
    Hear(
        R.drawable.hearing_select,
        R.drawable.hearing_no_select,
        R.string.text_hearing_impairment
    ),
    Visual(
        R.drawable.visual_select,
        R.drawable.visual_no_select,
        R.string.text_visual_impairment
    ),
    Elderly(
        R.drawable.elderly_people_select,
        R.drawable.elderly_people_no_select,
        R.string.text_elderly_person
    ),
    Child(
        R.drawable.infant_family_select,
        R.drawable.infant_family_no_select,
        R.string.text_infant_family
    )
}