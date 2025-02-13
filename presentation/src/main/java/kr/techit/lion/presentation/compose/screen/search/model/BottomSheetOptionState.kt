package kr.techit.lion.presentation.compose.screen.search.model

import java.util.TreeSet
import kr.techit.lion.presentation.compose.screen.search.model.Disability.PHYSICAL_DISABILITY
import kr.techit.lion.presentation.compose.screen.search.model.Disability.VISUAL_IMPAIRMENT
import kr.techit.lion.presentation.compose.screen.search.model.Disability.HEARING_IMPAIRMENT
import kr.techit.lion.presentation.compose.screen.search.model.Disability.INFANT_FAMILY
import kr.techit.lion.presentation.compose.screen.search.model.Disability.ELDERLY_PEOPLE

data class BottomSheetOptionState(
    val selectedDisabilityOptions: TreeSet<Int>,
    val selectedDisability: Disability,
    val currentSelectedOption: TreeSet<Int>,
) {
    val physicalOption
        get() = selectedDisabilityOptions.filter { PHYSICAL_DISABILITY.optionCodes.contains(it) }

    val visualOption
        get() = selectedDisabilityOptions.filter { VISUAL_IMPAIRMENT.optionCodes.contains(it) }

    val hearingOption
        get() = selectedDisabilityOptions.filter { HEARING_IMPAIRMENT.optionCodes.contains(it) }

    val infantOption
        get() = selectedDisabilityOptions.filter { INFANT_FAMILY.optionCodes.contains(it) }

    val elderlyOption
        get() = selectedDisabilityOptions.filter { ELDERLY_PEOPLE.optionCodes.contains(it) }

    val bottomSheetOptions
        get() = enumValues<FacilitiesForPersonWithDisability>()
            .filter {
                it.disabilityType == selectedDisability.code
            }

    fun addAllSpecificDisabilityOptions(): BottomSheetOptionState {
        return this.copy(
            currentSelectedOption = TreeSet(currentSelectedOption).apply {
                when (selectedDisability) {
                    PHYSICAL_DISABILITY -> {
                        addAll(PHYSICAL_DISABILITY.optionCodes)
                    }
                    VISUAL_IMPAIRMENT -> {
                        addAll(VISUAL_IMPAIRMENT.optionCodes)
                    }
                    HEARING_IMPAIRMENT -> {
                        addAll(HEARING_IMPAIRMENT.optionCodes)
                    }
                    INFANT_FAMILY -> {
                        addAll(INFANT_FAMILY.optionCodes)
                    }
                    ELDERLY_PEOPLE -> {
                        addAll(ELDERLY_PEOPLE.optionCodes)
                    }
                }
            }
        )
    }

    fun updateCurrentSelectedOption(optionCode: Int): BottomSheetOptionState {
        return this.copy(
            currentSelectedOption = TreeSet(currentSelectedOption).apply {
                if (contains(optionCode)) remove(optionCode)
                else add(optionCode)
            }
        )
    }

    fun clearCurrentSelectedOption(): BottomSheetOptionState {
        return this.copy(
            currentSelectedOption = TreeSet()
        )
    }

    fun updateOption(): BottomSheetOptionState {
        return this.copy(
            selectedDisabilityOptions = TreeSet(selectedDisabilityOptions).apply {
                currentSelectedOption.forEach { code ->
                    if (!contains(code)) add(code)
                    else remove(code)
                }
            },
            currentSelectedOption = TreeSet()
        )
    }

    fun updateDisability(disability: Disability): BottomSheetOptionState {
        return this.copy(
            selectedDisability = disability
        )
    }

    fun clear(): BottomSheetOptionState {
        return this.copy(
            selectedDisabilityOptions = TreeSet(),
        )
    }

    companion object {
        fun create(): BottomSheetOptionState {
            return BottomSheetOptionState(
                selectedDisabilityOptions = TreeSet(),
                selectedDisability = PHYSICAL_DISABILITY,
                currentSelectedOption = TreeSet()
            )
        }
    }
}