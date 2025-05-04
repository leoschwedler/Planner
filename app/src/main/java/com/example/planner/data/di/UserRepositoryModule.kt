package com.example.planner.data.di

import com.example.planner.data.datasource.local.AuthenticationLocalDataSourceImpl
import com.example.planner.data.repository.AuthenticationRepository
import com.example.planner.data.repository.AuthenticationRepositoryImpl
import com.example.planner.data.repository.UserRepository
import com.example.planner.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UserRepositoryModule {

    @Binds
    @Singleton
    fun bindsUserRepository(repository: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindsAuthenticationRepository(repository: AuthenticationRepositoryImpl): AuthenticationRepository

}