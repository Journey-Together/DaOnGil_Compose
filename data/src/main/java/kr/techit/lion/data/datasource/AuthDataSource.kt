package kr.techit.lion.data.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.techit.lion.data.common.execute
import kr.techit.lion.domain.exception.Result
import kr.techit.lion.network.service.AuthService
import kr.techit.lion.network.token.TokenDataSource
import kr.techit.lion.network.token.model.TokenData
import okhttp3.RequestBody
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val dataStore: TokenDataSource,
    private val authService: AuthService
) {
    private val data: Flow<TokenData>
        get() = dataStore.userToken

    val loggedIn: Flow<Boolean>
        get() = data.map { it.accessToken.isNotBlank() }

    suspend fun signIn(type: String, accessToken: String, refreshToken: RequestBody) = runCatching {
        authService.signIn(type = type, token = "Bearer $accessToken", requestBody = refreshToken)
    }

    suspend fun logout(): kotlin.Result<Unit> = runCatching {
        dataStore.clearTokenData()
        authService.signOut()
    }

    suspend fun withdraw(): Result<Unit> = execute {
        dataStore.clearTokenData()
        authService.withdraw()
    }
}