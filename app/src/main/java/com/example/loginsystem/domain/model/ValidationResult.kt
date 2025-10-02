package com.example.loginsystem.domain.model

sealed class ValidationResult {
    object Valid: ValidationResult()
    data class Invalid(val message: String): ValidationResult()
}