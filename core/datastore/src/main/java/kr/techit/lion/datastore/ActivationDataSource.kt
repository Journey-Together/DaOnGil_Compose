package kr.techit.lion.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.serialization.json.Json
import kr.techit.lion.datastore.di.ActivationDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActivationDataSource @Inject constructor(
    @ActivationDataStore private val dataStore: DataStore<Preferences>
) {
    private companion object PreferencesKey {
        private val KEY_ACTIVATION = booleanPreferencesKey("user_activation")
    }

    val activation: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[KEY_ACTIVATION] == true
    }

    suspend fun saveActivation() {
        dataStore.edit { preferences ->
            preferences[KEY_ACTIVATION] = true
        }
    }
}