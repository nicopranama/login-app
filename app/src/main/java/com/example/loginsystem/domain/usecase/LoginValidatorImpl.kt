package com.example.loginsystem.domain.usecase

import com.example.loginsystem.data.model.LoginCredentials
import com.example.loginsystem.domain.model.ValidationResult

class LoginValidatorImpl : LoginValidator {
    override fun validateUsername(username: String): ValidationResult {
        return when {
            username.isBlank() -> ValidationResult.Invalid("Username tidak boleh kosong")
            username.length < 3 -> ValidationResult.Invalid("Username minimal 3 karakter")
            else -> ValidationResult.Valid
        }
    }

    override fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult.Invalid("Password tidak boleh kosong")
            password.length < 6 -> ValidationResult.Invalid("Password minimal 6 karakter")
            else -> ValidationResult.Valid
        }
    }

    override fun validateCredentials(credentials: LoginCredentials): ValidationResult {
        val usernameValidation = validateUsername(credentials.username)
        if (usernameValidation is ValidationResult.Invalid) return usernameValidation

        val passwordValidation = validatePassword(credentials.password)
        if (passwordValidation is ValidationResult.Invalid) return passwordValidation

        return ValidationResult.Valid
    }
}