package com.example.planner.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planner.data.model.Profile
import com.example.planner.data.repository.UserRepository
import com.example.planner.presentation.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRegistrationViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ProfileState())
    val uiState = _uiState.asStateFlow()

    init {
        observerProfile()
    }

    private fun observerProfile() {
        viewModelScope.launch {
            userRepository.getUserProfile().catch {
                Log.d("UserRegistrationViewModel", "Erro ao obter profile: ${it.message}")
            }.collect { profile ->
                _uiState.update {
                    it.copy(
                        name = profile.name,
                        email = profile.email,
                        telephone = profile.telephone,
                        image = profile.image
                    )
                }
            }
        }
    }

    fun getIsUserRegistered(): Boolean {
        return userRepository.isUserRegistered().fold(
            onSuccess = { it },
            onFailure = { false }
        )
    }

    fun saveProfile() {
        viewModelScope.launch {
            val profile = Profile(
                name = uiState.value.name,
                email = uiState.value.email,
                telephone = uiState.value.telephone,
                image = uiState.value.image
            )
            userRepository.saveUserProfile(profile)
            userRepository.saveIsUserRegistered(isRegistered = true)
        }
    }


    fun updateName(name: String) {
        _uiState.update { currentState ->
            val updateState = currentState.copy(name = name)
            updateState.copy(isProfileValid = validadeteProfile(updateState))
        }
    }

    fun updateEmail(email: String) {
        _uiState.update { currentState ->
            val updateState = currentState.copy(email = email)
            updateState.copy(isProfileValid = validadeteProfile(updateState))
        }
    }

    fun updateTelephone(telephone: String) {
        _uiState.update { currentState ->
            val updateState = currentState.copy(telephone = telephone)
            updateState.copy(isProfileValid = validadeteProfile(updateState))
        }
    }

    fun updateImage(image: String) {
        _uiState.update { currentState ->
            val updateState = currentState.copy(image = image)
            updateState.copy(isProfileValid = validadeteProfile(updateState))
        }
    }

    private fun validadeteProfile(
        state: ProfileState
    ): Boolean {
        return state.name.isNotBlank() && state.email.isNotBlank() && state.telephone.isNotBlank() && state.image.isNotBlank()
    }

}