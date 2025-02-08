package kr.techit.lion.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kr.techit.lion.datastore.di.ActivationDataStore
import javax.inject.Inject

class ActivationDataSource @Inject constructor(
    @ActivationDataStore private val dataStore: DataStore<Preferences>
) {
    private companion object PreferencesKey {
        private val KEY_ACTIVATION = booleanPreferencesKey("user_activation")
    }

    suspend fun checkActivation(): Boolean {
        return dataStore.data.first()[KEY_ACTIVATION] != null
    }

    suspend fun saveActivation() {
        dataStore.edit { preferences ->
            preferences[KEY_ACTIVATION] = true
        }
    }
}