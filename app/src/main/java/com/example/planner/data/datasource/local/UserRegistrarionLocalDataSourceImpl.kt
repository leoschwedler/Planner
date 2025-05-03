package com.example.planner.data.datasource.local

import android.app.Application
import android.content.Context
import javax.inject.Inject

private const val USER_REGISTRATION_FILE_NAME = "user_registration"
private const val IS_USER_REGISTERED = "is_user_registered"


class UserRegistrarionLocalDataSourceImpl @Inject constructor(private val applicationContext: Application) :
    UserRegistrarionLocalDataSource {

    private val sharedPreferences =
        applicationContext.getSharedPreferences(USER_REGISTRATION_FILE_NAME, Context.MODE_PRIVATE)

    override fun isUserRegistered(): Boolean {
        return sharedPreferences.getBoolean(IS_USER_REGISTERED, false)
    }

    override fun saveIsUserRegistered(isRegistered: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(IS_USER_REGISTERED, isRegistered)
            apply()
        }
    }
}