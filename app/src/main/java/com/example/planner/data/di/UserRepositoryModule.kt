package com.example.planner.data.di

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
}