package kr.techit.lion.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.techit.lion.datastore.Activation
import kr.techit.lion.datastore.ActivationDataSource
import kr.techit.lion.domain.repository.ActivationRepository
import javax.inject.Inject

internal class ActivationRepositoryImpl @Inject constructor(
    private val activationDataSource: ActivationDataSource
) : ActivationRepository {

    override val activation: Flow<Boolean>
        get() = activationDataSource.activation.map { activation ->
            when (activation) {
                is Activation.Activate -> true
                is Activation.DeActivate -> false
            }
        }

    override suspend fun saveUserActivation() {
        activationDataSource.saveActivation()
    }
}