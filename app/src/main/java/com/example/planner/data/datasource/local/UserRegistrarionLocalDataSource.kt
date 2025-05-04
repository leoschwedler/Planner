package com.example.planner.data.datasource.local

import com.example.planner.data.model.Profile
import kotlinx.coroutines.flow.Flow

interface UserRegistrarionLocalDataSource {

    fun isUserRegistered(): Boolean

    fun saveIsUserRegistered(isRegistered: Boolean)

    val profile: Flow<Profile>

    suspend fun saveProfile(profile: Profile)
}