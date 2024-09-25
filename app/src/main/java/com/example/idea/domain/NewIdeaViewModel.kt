package com.example.idea.domain

import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idea.domain.utils.Constants
import com.example.idea.model.IdeaDataForInsert
import com.example.idea.view.MainActivity.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewIdeaViewModel @Inject constructor() : ViewModel() {
    private val _navigationStateFlow : MutableStateFlow<Routes?> = MutableStateFlow(null)
    val navigationStateFlow : StateFlow<Routes?> = _navigationStateFlow.asStateFlow()

    private var _editedIdea = mutableStateOf(IdeaDataForInsert("", Constants.supabase.auth.currentUserOrNull()!!.id, "", 1))
    val editedIdea by _editedIdea

    val isButtonEnabled = derivedStateOf { _editedIdea.value.name.isNotBlank() && _editedIdea.value.description.isNotBlank() }

    private fun resetEditedIdea(){
        _editedIdea = mutableStateOf(IdeaDataForInsert("", Constants.supabase.auth.currentUserOrNull()!!.id, "", 1))
    }

    fun updateEditedIdea(newEditedIdea: IdeaDataForInsert) {
        _editedIdea.value = newEditedIdea
    }

    fun navigateToMain() {
        viewModelScope.launch {
            _navigationStateFlow.value = Routes.Main
            resetEditedIdea()
        }
    }

    fun addIdea() {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    Constants.supabase.from("ideas").insert(_editedIdea.value)
                }
            }
        }
        catch (e: Exception) {
            Log.e("supabase", "addIdea: $e", )
        }
    }
}