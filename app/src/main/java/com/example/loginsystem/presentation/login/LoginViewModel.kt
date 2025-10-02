package com.example.loginsystem.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginsystem.data.model.LoginCredentials
import com.example.loginsystem.data.repository.AuthRepository
import com.example.loginsystem.data.repository.AuthRepositoryImpl
import com.example.loginsystem.domain.model.LoginUiState
import com.example.loginsystem.domain.model.ValidationResult
import com.example.loginsystem.domain.usecase.LoginValidator
import com.example.loginsystem.domain.usecase.LoginValidatorImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validator: LoginValidator = LoginValidatorImpl(),
    private val repository: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun onUsernameChange(value: String) {
        _username.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun onLoginClick() {
        val credentials = LoginCredentials(
            username = _username.value.trim(),
            password = _password.value
        )

        when (val result = validator.validateCredentials(credentials)) {
            is ValidationResult.Invalid -> {
                _uiState.value = LoginUiState.Error(result.message)
            }
            is ValidationResult.Valid -> {
                performLogin(credentials)
            }
        }
    }

    private fun performLogin(credentials: LoginCredentials) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            repository.login(credentials)
                .onSuccess { message ->
                    _uiState.value = LoginUiState.Success(message)
                }
                .onFailure { exception ->
                    _uiState.value = LoginUiState.Error(
                        exception.message ?: "Terjadi kesalahan"
                    )
                }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}