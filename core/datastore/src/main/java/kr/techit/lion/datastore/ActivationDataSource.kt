package kr.techit.lion.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.serialization.json.Json
import kr.techit.lion.datastore.di.ActivationDataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActivationDataSource @Inject constructor(
    @ActivationDataStore private val dataStore: DataStore<Preferences>
) {
    private companion object PreferencesKey {
        private val KEY_ACTIVATION = stringPreferencesKey("user_activation")
    }

    val activation: Flow<Activation> = dataStore.data.map { preferences ->
        val jsonString = preferences[KEY_ACTIVATION] ?: return@map Activation.DeActivate
        Json.decodeFromString(Activation.serializer(), jsonString)
    }

    suspend fun saveActivation(){
        val jsonString = Json.encodeToString(Activation.serializer(), Activation.Activate)
        dataStore.edit { preferences ->
            preferences[KEY_ACTIVATION] = jsonString
        }
    }
}