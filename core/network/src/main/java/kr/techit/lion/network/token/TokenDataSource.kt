package kr.techit.lion.network.token

import kotlinx.coroutines.flow.Flow
import kr.techit.lion.network.token.model.TokenData

interface TokenDataSource {
    val userToken: Flow<TokenData>
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String
    suspend fun saveUserToken(tokenData: TokenData)
    suspend fun clearTokenData()
}
