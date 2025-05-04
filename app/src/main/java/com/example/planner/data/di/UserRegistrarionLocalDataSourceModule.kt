package com.example.planner.data.di

import com.example.planner.data.datasource.local.AuthenticationLocalDataSource
import com.example.planner.data.datasource.local.AuthenticationLocalDataSourceImpl
import com.example.planner.data.datasource.local.UserRegistrarionLocalDataSource
import com.example.planner.data.datasource.local.UserRegistrarionLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UserRegistrarionLocalDataSourceModule {

    @Binds
    @Singleton
    fun bindsUserRegistrarionLocalDataSource(userRegistrarionLocalDataSourceImpl: UserRegistrarionLocalDataSourceImpl): UserRegistrarionLocalDataSource

    @Binds
    @Singleton
    fun bindsAuthenticationLocalDataSource(authenticationLocalDataSourceImpl: AuthenticationLocalDataSourceImpl): AuthenticationLocalDataSource
}