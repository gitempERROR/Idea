package com.example.idea.domain

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idea.domain.utils.Constants.supabase
import com.example.idea.view.MainActivity.navigation.Routes
import com.example.idea.model.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _navigationStateFlow: MutableStateFlow<Routes?> = MutableStateFlow(null)
    private val _loginState: MutableState<LoginState> = mutableStateOf(LoginState("", ""))

    val navigationStateFlow: StateFlow<Routes?> = _navigationStateFlow.asStateFlow()
    val loginState: LoginState get() = _loginState.value

    val isButtonEnabled by derivedStateOf { _loginState.value.login.isNotBlank() && _loginState.value.password.isNotBlank() }

    private var _isError = mutableStateOf(false)
    val isError by _isError

    fun updateLoginState(newLoginState: LoginState) {
        _loginState.value = newLoginState
    }

    fun login(){
        viewModelScope.launch {
            try {
                Log.d("login", loginState.password)
                val user = supabase.auth.signInWith(Email){
                    email = loginState.login
                    password = loginState.password
                }
                _navigationStateFlow.value = Routes.Main
            }
            catch (e: Exception) {
                _isError.value = true
            }
        }
    }
}