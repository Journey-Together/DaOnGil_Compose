package kr.techit.lion.presentation.compose.screen.concern.vm

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.techit.lion.domain.model.ConcernType
import kr.techit.lion.domain.repository.MemberRepository
import kr.techit.lion.presentation.base.BaseViewModel
import kr.techit.lion.presentation.compose.screen.concern.model.ConcernResource
import kr.techit.lion.presentation.delegate.NetworkEventDelegate
import javax.inject.Inject

@HiltViewModel
class ConcernViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val networkEventDelegate: NetworkEventDelegate
): BaseViewModel() {

    val networkEvent get() = networkEventDelegate.event

    private val _selectedType = MutableStateFlow<List<ConcernResource>>(emptyList())
    val selectedType get() = _selectedType.asStateFlow()

    fun onSelectInterest(type: ConcernResource) {
        val currentValue = _selectedType.value

        if (currentValue.contains(type)) {
            _selectedType.value = currentValue - type
        } else {
            _selectedType.value = currentValue + type
        }
    }

    fun onClickSubmitButton(){
        val currentValues = _selectedType.value
        val model = ConcernType(
            isPhysical = currentValues.contains(ConcernResource.Physical),
            isHear = currentValues.contains(ConcernResource.Hear),
            isVisual = currentValues.contains(ConcernResource.Visual),
            isElderly = currentValues.contains(ConcernResource.Elderly),
            isChild = currentValues.contains(ConcernResource.Child)
        )
        execute(
            action = {
                memberRepository.updateConcernType(model)
            },
            eventHandler = networkEventDelegate,
            onSuccess = {}
        )
    }
}