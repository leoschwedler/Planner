package com.example.planner.data.repository

import com.example.planner.data.model.Profile
import kotlinx.coroutines.flow.Flow

interface UserRepository {

     fun getUserProfile(): Flow<Profile>

    suspend fun saveUserProfile(profile: Profile)

    fun isUserRegistered(): Result<Boolean>

    fun saveIsUserRegistered(isRegistered: Boolean)
}