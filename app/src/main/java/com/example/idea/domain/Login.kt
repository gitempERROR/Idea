package com.example.idea.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.idea.domain.navigation.Routes
import com.example.idea.model.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class Login @Inject constructor() : ViewModel() {
    private val _navigationStateFlow: MutableStateFlow<Routes?> = MutableStateFlow(null)
    private val _loginState: MutableState<LoginState> = mutableStateOf(LoginState("", ""))

    val navigationEvent: StateFlow<Routes?> = _navigationStateFlow.asStateFlow()
    val loginState: LoginState get() = _loginState.value

    val isButtonEnabled = derivedStateOf { _loginState.value.login.isNotBlank() && _loginState.value.password.isNotBlank() }

    fun updateLoginState(newLoginState: LoginState) {
        _loginState.value = newLoginState
    }
}