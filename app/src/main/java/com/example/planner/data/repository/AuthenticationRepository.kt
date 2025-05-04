package com.example.planner.data.repository

import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    suspend fun insertToken(token: String)

     fun getToken(): Flow<String>

     fun getExpirationDateTime(): Flow<Long>

}