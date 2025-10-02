package com.example.loginsystem.data.repository

import com.example.loginsystem.data.model.LoginCredentials

interface AuthRepository {
    suspend fun login(credentials: LoginCredentials): Result<String>
}