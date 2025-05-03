package com.example.planner.data.datasource.local

interface UserRegistrarionLocalDataSource {

    fun isUserRegistered(): Boolean

    fun saveIsUserRegistered(isRegistered: Boolean)
}