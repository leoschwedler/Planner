package com.example.planner.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.planner.data.datasource.local.UserRegistrarionLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserRegistrationViewModel @Inject constructor(private val userRegistrarionLocalDataSource: UserRegistrarionLocalDataSource) :
    ViewModel() {

    fun getIsUserRegistered(): Boolean {
        return userRegistrarionLocalDataSource.isUserRegistered()
    }

    fun saveIsUserRegistered(isRegistered: Boolean) {
        userRegistrarionLocalDataSource.saveIsUserRegistered(isRegistered = isRegistered)
    }
}