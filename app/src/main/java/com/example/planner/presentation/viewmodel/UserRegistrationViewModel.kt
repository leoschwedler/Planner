package com.example.planner.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planner.data.model.Profile
import com.example.planner.data.repository.AuthenticationRepository
import com.example.planner.data.repository.UserRepository
import com.example.planner.presentation.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private val mockToken =
    """ eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30 """

@HiltViewModel
class UserRegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authenticationRepository: AuthenticationRepository
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ProfileState())
    val uiState = _uiState.asStateFlow()

    init {
        observerProfile()
    }

    private fun observerProfile() {
        viewModelScope.apply {
            launch {
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
            launch {
                while (true) {
                    val tokenExpirationDateTime =
                        authenticationRepository.getExpirationDateTime().firstOrNull()
                    tokenExpirationDateTime?.let { tokenExpirationDateTime ->
                        val dateTimeNow = System.currentTimeMillis()
                        Log.d("CheckToken", "Token valid: ${_uiState.value.isTokenValid}")
                        _uiState.update { it.copy(isTokenValid = tokenExpirationDateTime >= dateTimeNow) }
                    }
                    delay(5_000)
                }
            }
        }

    }

    fun obtainNewToken() {
        viewModelScope.launch {
            authenticationRepository.insertToken(token = mockToken)
            _uiState.update { it.copy(isTokenValid = true) }
        }

    }

    fun getIsUserRegistered(): Boolean {
        return userRepository.isUserRegistered().fold(
            onSuccess = { it },
            onFailure = { false }
        )
    }

    fun saveProfile( onCompleted : () -> Unit) {
        viewModelScope.launch {
            async {
                val profile = Profile(
                    name = uiState.value.name,
                    email = uiState.value.email,
                    telephone = uiState.value.telephone,
                    image = uiState.value.image
                )
                Log.d("ProtoSaveError", "Chamando saveProfile com: $profile")
                userRepository.saveUserProfile(profile)
                userRepository.saveIsUserRegistered(isRegistered = true)
                authenticationRepository.insertToken(token = mockToken)
                _uiState.update { it.copy(isTokenValid = true) }
            }.await()
            onCompleted()
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