package com.example.planner.data.repository

import com.example.planner.data.datasource.local.AuthenticationLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(private val authenticationLocalDataSource: AuthenticationLocalDataSource): AuthenticationRepository  {
    override suspend fun insertToken(token: String) {
        authenticationLocalDataSource.insertToken(token = token)
    }

    override fun getToken(): Flow<String> {
        return authenticationLocalDataSource.token
    }

    override fun getExpirationDateTime(): Flow<Long> {
        return authenticationLocalDataSource.expirationDateTime
    }
}