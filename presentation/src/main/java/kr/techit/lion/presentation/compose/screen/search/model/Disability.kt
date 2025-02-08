package kr.techit.lion.presentation.compose.screen.search.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kr.techit.lion.presentation.R
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability.PARKING
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability.WHEELCHAIR
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability.ELEVATOR
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability.RESTROOM
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability.SEAT
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability.BRAILEBLOCK
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability.HELP_DOG
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability.GUIDE
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability.AUDIO_GUIDE
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability.SIGN_GUIDE
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability.VIDEO_GUIDE
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability.STROLLER
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability.LACTATION_ROOM
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability.BABY_SPARE_CHAIR
import kr.techit.lion.presentation.compose.screen.search.model.FacilitiesForPersonWithDisability.WHEELCHAIR_LENT

enum class Disability(
    val code: Long,
    val optionCodes: List<Int>,
    @DrawableRes val unSelectedImageResourceId: Int,
    @DrawableRes val selectedImageResourceId: Int,
    @StringRes val contentDescriptionResourceId: Int
) {
    PHYSICAL_DISABILITY(
        1,
        listOf(PARKING.code, WHEELCHAIR.code, ELEVATOR.code, RESTROOM.code, SEAT.code),
        R.drawable.sv_physical_disability_unselected_icon,
        R.drawable.sv_physical_disability_selected_icon,
        R.string.text_physical_disability
    ),
    VISUAL_IMPAIRMENT(
        2,
        listOf(BRAILEBLOCK.code, HELP_DOG.code, GUIDE.code, AUDIO_GUIDE.code),
        R.drawable.sv_visual_impairment_unselect_icon,
        R.drawable.sv_visual_impairment_select_icon,
        R.string.text_visual_impairment
    ),
    HEARING_IMPAIRMENT(
        3,
        listOf(SIGN_GUIDE.code, VIDEO_GUIDE.code),
        R.drawable.sv_hearing_impaired_unselected_icon,
        R.drawable.sv_hearing_impaired_selected_icon,
        R.string.text_hearing_impairment
    ),
    INFANT_FAMILY(
        4,
        listOf(STROLLER.code, LACTATION_ROOM.code, BABY_SPARE_CHAIR.code),
        R.drawable.sv_child_unselected_icon,
        R.drawable.sv_child_selected_icon,
        R.string.text_infant_family
    ),
    ELDERLY_PEOPLE(
        5,
        listOf(WHEELCHAIR_LENT.code),
        R.drawable.sv_elderly_unselected_icon,
        R.drawable.sv_elderly_selected_icon,
        R.string.text_elderly_person
    )
}