package com.example.loginsystem.domain.model

sealed class LoginUiState {
    object Idle: LoginUiState()
    object Loading: LoginUiState()
    data class Error(val message: String): LoginUiState()
    data class Success(val message: String): LoginUiState()
}