package com.example.planner.presentation.state

data class ProfileState(
    val name: String = "",
    val email: String = "",
    val telephone: String = "",
    val image: String = "",
    val isProfileValid: Boolean = false
)