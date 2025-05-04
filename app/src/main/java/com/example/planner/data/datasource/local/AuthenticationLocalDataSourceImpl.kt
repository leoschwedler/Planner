package com.example.planner.data.datasource.local

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val AUTHENTICATION_FILE_NAME = "authentication"
private const val TOKEN_KEY = "token"
private  val TOKEN_PREFERENCES_KEY = stringPreferencesKey(TOKEN_KEY)
private const val EXPIRATION_DATETIME_KEY = "expiration_date_time"
private val EXPIRATION_DATETIME_PREFERENCES_KEY = longPreferencesKey(EXPIRATION_DATETIME_KEY)
private const val ADDITIONAL_EXPIRATION_DATE_TIME = 10_000


class AuthenticationLocalDataSourceImpl @Inject constructor(private val application: Application): AuthenticationLocalDataSource {

    private val Context.authenticationPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = AUTHENTICATION_FILE_NAME
    )

    override val token: Flow<String>
        get() = application.authenticationPreferencesDataStore.data.map { settings ->
            settings[TOKEN_PREFERENCES_KEY].orEmpty()
        }
    override val expirationDateTime: Flow<Long>
        get() = application.authenticationPreferencesDataStore.data.map { settings ->
            settings[EXPIRATION_DATETIME_PREFERENCES_KEY] ?: 0
        }

    override suspend fun insertToken(token: String) {
        application.authenticationPreferencesDataStore.edit { settings ->
            settings[TOKEN_PREFERENCES_KEY] = token
            settings[EXPIRATION_DATETIME_PREFERENCES_KEY] = System.currentTimeMillis() + ADDITIONAL_EXPIRATION_DATE_TIME
        }
    }
}