package com.example.planner.data.datasource.local

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.planner.data.model.Profile
import com.example.planner.data.model.ProfileSerializer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val USER_REGISTRATION_FILE_NAME = "user_registration"
private const val PROFILE_FILE_NAME = "profile.db"
private const val IS_USER_REGISTERED = "is_user_registered"




class UserRegistrarionLocalDataSourceImpl @Inject constructor(private val applicationContext: Application) :
    UserRegistrarionLocalDataSource {

    private val sharedPreferences =
        applicationContext.getSharedPreferences(USER_REGISTRATION_FILE_NAME, Context.MODE_PRIVATE)

    private val Context.protoDataStore: DataStore<Profile> by dataStore(
        fileName = PROFILE_FILE_NAME,
        serializer = ProfileSerializer
    )

    override fun isUserRegistered(): Boolean {
        return sharedPreferences.getBoolean(IS_USER_REGISTERED, false)
    }

    override fun saveIsUserRegistered(isRegistered: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(IS_USER_REGISTERED, isRegistered)
            apply()
        }
    }

    override val profile: Flow<Profile>
        get() = applicationContext.protoDataStore.data

    override suspend fun saveProfile(profile: Profile) {
        try {
            applicationContext.protoDataStore.updateData {
                profile
            }
        } catch (e: Exception) {
            Log.e("ProtoSaveError", "Erro ao salvar no ProtoDataStore: ${e.message}", e)
        }
    }
}