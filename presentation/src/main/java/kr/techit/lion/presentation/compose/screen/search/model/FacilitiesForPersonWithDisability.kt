package kr.techit.lion.presentation.compose.screen.search.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kr.techit.lion.presentation.R

enum class FacilitiesForPersonWithDisability(
    val disabilityType: Long,
    val code: Int,
    @DrawableRes val iconResourceId: Int,
    @StringRes val contentDescriptionResourceId: Int
) {
    PARKING(1, 1, R.drawable.chip_parking_icon, R.string.text_parking),
    WHEELCHAIR(1, 6, R.drawable.chip_wheelchair_icon, R.string.text_wheelchair),
    ELEVATOR(1, 7, R.drawable.chip_elevator_icon, R.string.text_elevator),
    RESTROOM(1, 8, R.drawable.chip_restroom_icon, R.string.text_restroom),
    SEAT(1, 9, R.drawable.chip_seat_icon, R.string.text_auditorium),
    BRAILEBLOCK(2, 13, R.drawable.chip_dot_icon, R.string.text_braile_block),
    HELP_DOG(2, 14, R.drawable.chip_help_dog_icon, R.string.text_help_dog),
    GUIDE(2, 15, R.drawable.chip_guide_icon, R.string.text_guide),
    AUDIO_GUIDE(2, 16, R.drawable.chip_audio_guide_icon, R.string.text_audio_guide),
    SIGN_GUIDE(3, 21, R.drawable.chip_sign_icon, R.string.text_sign_guide),
    VIDEO_GUIDE(3, 22, R.drawable.chip_cc_icon, R.string.text_video_guide),
    STROLLER(4, 25, R.drawable.chip_stroller_icon, R.string.text_stroller),
    LACTATION_ROOM(4, 26, R.drawable.chip_lactationroom_icon, R.string.text_lactation),
    BABY_SPARE_CHAIR(4, 27, R.drawable.chip_baby_spare_chair_icon, R.string.text_baby_spare_chair),
    WHEELCHAIR_LENT(5, 29, R.drawable.chip_wheelchair_icon, R.string.text_lend)
}