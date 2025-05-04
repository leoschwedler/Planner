package com.example.planner.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class Profile(
    val name: String = "",
    val email: String = "",
    val telephone: String = "",
    val image: String = "",
)
