package com.example.loginsystem.data.repository

import com.example.loginsystem.data.model.LoginCredentials
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(credentials: LoginCredentials): Result<String> {
        delay(1500)

        return if (credentials.username.equals("admin", ignoreCase = true) &&
            credentials.password == "123456") {
            Result.success("Login berhasil!")
        } else {
            Result.failure(Exception("Username atau password salah"))
        }
    }
}