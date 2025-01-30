package kr.techit.lion.domain.repository

import kotlinx.coroutines.flow.Flow

interface ActivationRepository {
    val activation: Flow<Boolean>
    suspend fun saveUserActivation()
}