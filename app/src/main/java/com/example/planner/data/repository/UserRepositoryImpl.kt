package com.example.planner.data.repository

import com.example.planner.data.datasource.local.UserRegistrarionLocalDataSource
import com.example.planner.data.model.Profile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val localDataSource: UserRegistrarionLocalDataSource) :
    UserRepository {
    override fun getUserProfile(): Flow<Profile> {
        return localDataSource.profile
    }

    override suspend fun saveUserProfile(profile: Profile) {
        localDataSource.saveProfile(profile)
    }

    override fun isUserRegistered(): Result<Boolean> {
       return runCatching {
            localDataSource.isUserRegistered()
        }
    }

    override fun saveIsUserRegistered(isRegistered: Boolean) {
        localDataSource.saveIsUserRegistered(isRegistered)
    }
}