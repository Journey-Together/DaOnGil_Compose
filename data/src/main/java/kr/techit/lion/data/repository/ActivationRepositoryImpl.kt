package kr.techit.lion.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.techit.lion.datastore.ActivationDataSource
import kr.techit.lion.domain.repository.ActivationRepository
import javax.inject.Inject

internal class ActivationRepositoryImpl @Inject constructor(
    private val activationDataSource: ActivationDataSource
) : ActivationRepository {

    override suspend fun checkUserActivation(): Boolean {
        return withContext(Dispatchers.IO) {
            activationDataSource.checkActivation()
        }
    }
    override suspend fun saveUserActivation() {
        withContext(Dispatchers.IO) {
            activationDataSource.saveActivation()
        }
    }
}