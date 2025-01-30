package kr.techit.lion.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kr.techit.lion.datastore.di.TokenDataStore
import kr.techit.lion.network.token.model.TokenData
import kr.techit.lion.network.token.TokenDataSource
import javax.inject.Inject

class TokenDataSourceImpl @Inject constructor(
    @TokenDataStore private val dataStore: DataStore<Preferences>,
) : TokenDataSource {
    private companion object PreferencesKey {
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    override val userToken: Flow<TokenData> = dataStore.data.map { preferences ->
        TokenData(
            accessToken = preferences[KEY_ACCESS_TOKEN] ?: "",
            refreshToken = preferences[KEY_REFRESH_TOKEN] ?: ""
        )
    }

    override suspend fun getAccessToken(): String {
        return userToken.first().accessToken
    }

    override suspend fun getRefreshToken(): String {
        return userToken.first().refreshToken
    }

    override suspend fun saveUserToken(tokenData: TokenData) {
        dataStore.edit { preferences ->
            preferences[KEY_ACCESS_TOKEN] = tokenData.accessToken
            preferences[KEY_REFRESH_TOKEN] = tokenData.refreshToken
        }
    }

    override suspend fun clearTokenData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}