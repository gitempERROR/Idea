package com.example.idea.domain

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idea.domain.utils.Constants
import com.example.idea.model.IdeaData
import com.example.idea.view.MainActivity.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _navigationStateFlow : MutableStateFlow<Routes?> = MutableStateFlow(null)
    val navigationStateFlow : StateFlow<Routes?> = _navigationStateFlow.asStateFlow()

    fun navigateToLogin() {
        viewModelScope.launch {
            _navigationStateFlow.value = Routes.Login
        }
    }

    private var _ideasList : MutableStateFlow<MutableList<IdeaData>> = MutableStateFlow(mutableListOf())
    val ideasList: StateFlow<MutableList<IdeaData>> = _ideasList.asStateFlow()

    fun subscribeToData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                @OptIn(SupabaseExperimental::class)
                val ideaFlow: Flow<List<IdeaData>> = Constants.supabase.from("ideas").selectAsFlow(
                    IdeaData::id
                )
                ideaFlow.collect {
                    Log.d("ideasList", it.size.toString())
                    _ideasList.value = it.toMutableList()
                }
            }
        }
    }


    private var _editedIdea = mutableStateOf(IdeaData(ideasList.value.size,"", "", ""))
    val editedIdea = _editedIdea.value

    fun addIdea() {
        _ideasList.value.add(editedIdea)
    }

    fun writeName(newValue: String) {
        _editedIdea.value.name = newValue
    }

    fun writeDescription(newValue: String) {
        _editedIdea.value.description = newValue
    }
}