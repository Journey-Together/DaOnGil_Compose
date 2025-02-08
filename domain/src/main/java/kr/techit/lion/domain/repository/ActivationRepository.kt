package kr.techit.lion.domain.repository

interface ActivationRepository {
    suspend fun checkUserActivation(): Boolean
    suspend fun saveUserActivation()
}