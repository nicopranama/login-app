package com.example.loginsystem.domain.usecase

import com.example.loginsystem.data.model.LoginCredentials
import com.example.loginsystem.domain.model.ValidationResult

interface LoginValidator {
    fun validateUsername(username: String): ValidationResult
    fun validatePassword(password: String): ValidationResult
    fun validateCredentials(credentials: LoginCredentials): ValidationResult
}