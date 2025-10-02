package com.example.loginsystem.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loginsystem.domain.model.LoginUiState
import com.example.loginsystem.presentation.login.components.LoginButton
import com.example.loginsystem.presentation.login.components.PasswordTextField
import com.example.loginsystem.presentation.login.components.UsernameTextField

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is LoginUiState.Success) {
            onLoginSuccess()
        }
    }

    Scaffold(
        snackbarHost = {
            if (uiState is LoginUiState.Error || uiState is LoginUiState.Success) {
                val message = when (val state = uiState) {
                    is LoginUiState.Error -> state.message
                    is LoginUiState.Success -> state.message
                    else -> ""
                }

                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        TextButton(onClick = { viewModel.resetState() }) {
                            Text("OK")
                        }
                    }
                ) {
                    Text(message)
                }
            }
        }
    ) { padding ->
        LoginContent(
            modifier = Modifier.padding(padding),
            username = username,
            password = password,
            isLoading = uiState is LoginUiState.Loading,
            onUsernameChange = viewModel::onUsernameChange,
            onPasswordChange = viewModel::onPasswordChange,
            onLoginClick = viewModel::onLoginClick
        )
    }
}

@Composable
private fun LoginContent(
    modifier: Modifier = Modifier,
    username: String,
    password: String,
    isLoading: Boolean,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        UsernameTextField(
            value = username,
            onValueChange = onUsernameChange,
            enabled = !isLoading,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            enabled = !isLoading,
            onDone = {
                focusManager.clearFocus()
                onLoginClick()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        LoginButton(
            onClick = onLoginClick,
            enabled = !isLoading && username.isNotBlank() && password.isNotBlank(),
            isLoading = isLoading
        )

        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}