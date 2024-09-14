package com.example.idea.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idea.view.MainActivity.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {
    private val _navigationStateFlow : MutableStateFlow<Routes?> = MutableStateFlow(null)
    val navigationStateFlow : StateFlow<Routes?> = _navigationStateFlow.asStateFlow()

    fun launch() {
        viewModelScope.launch {
            delay(2000)
            _navigationStateFlow.value = Routes.Login
        }
    }
}