package kr.techit.lion.presentation.compose.screen.intro.concern

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.screen.intro.concern.vm.model.Disability

enum class ConcernResource(
    val type: Disability,
    @DrawableRes val selectedResourceId: Int,
    @DrawableRes val unselectedResourceId: Int,
    @StringRes val contentDescriptionId: Int
) {
    Physical(
        Disability.PhysicalDisability,
        R.drawable.physical_select,
        R.drawable.physical_no_select,
        R.string.text_physical_disability
    ),
    Hear(
        Disability.HearingImpairment,
        R.drawable.hearing_select,
        R.drawable.hearing_no_select,
        R.string.text_hearing_impairment
    ),
    Visual(
        Disability.VisualImpairment,
        R.drawable.visual_select,
        R.drawable.visual_no_select,
        R.string.text_visual_impairment
    ),
    Elderly(
        Disability.ElderlyPeople,
        R.drawable.elderly_people_select,
        R.drawable.elderly_people_no_select,
        R.string.text_elderly_person
    ),
    Child(
        Disability.InfantFamily,
        R.drawable.infant_family_select,
        R.drawable.infant_family_no_select,
        R.string.text_infant_family
    )
}