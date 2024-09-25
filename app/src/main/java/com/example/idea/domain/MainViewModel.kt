package com.example.idea.domain

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idea.domain.utils.Constants
import com.example.idea.model.IdeaData
import com.example.idea.model.RoleId
import com.example.idea.view.MainActivity.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val bucket = Constants.supabase.storage.from("images")

    var icons: MutableList<String> = mutableListOf("", "", "")

    private val _navigationStateFlow: MutableStateFlow<Routes?> = MutableStateFlow(null)
    val navigationStateFlow: StateFlow<Routes?> = _navigationStateFlow.asStateFlow()

    private var _ideasList: MutableStateFlow<MutableList<IdeaData>> = MutableStateFlow(mutableListOf())
    val ideasList: StateFlow<MutableList<IdeaData>> = _ideasList.asStateFlow()

    private var _selectedIdea = mutableStateOf(
        IdeaData(
            ideasList.value.size,
            "",
            Constants.supabase.auth.currentUserOrNull()!!.id,
            "",
            1
        )
    )
    val selectedIdea by _selectedIdea

    private var _userRole: MutableState<RoleId> = mutableStateOf(RoleId(0))
    val userRole by _userRole
    private var userId: String = ""

    private var _showExit = mutableStateOf(false)
    val showExit by _showExit

    fun showExitButton(){
        viewModelScope.launch {
            _showExit.value = true
            delay(2000)
            _showExit.value = false
        }
    }

    fun navigateToLogin() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Constants.supabase.auth.signOut()
            }
            _navigationStateFlow.value = Routes.Login
        }
    }

    fun navigateToNewIdea() {
        viewModelScope.launch {
            _navigationStateFlow.value = Routes.NewIdea
        }
    }

    fun navigateToIdea(ideaData: IdeaData) {
        _selectedIdea.value = ideaData
        viewModelScope.launch {
            _navigationStateFlow.value = Routes.Idea
        }
    }

    private fun getImages() {
        try {
            icons[0] = bucket.publicUrl("ongoing.png")
            icons[1] = bucket.publicUrl("refuse.png")
            icons[2] = bucket.publicUrl("accept.png")
        } catch (e: Exception) {
            Log.e("supabase", "getImages: $e")
        }
    }

    fun subscribeToData() {
        try {
            viewModelScope.launch {
                getUserIdAndRole()
                withContext(Dispatchers.IO) {
                    @OptIn(SupabaseExperimental::class)
                    val ideaFlow: Flow<List<IdeaData>> = Constants.supabase.from("ideas").selectAsFlow(
                        IdeaData::id,
                        filter = if (userRole.role_id == 1) FilterOperation(
                            "author",
                            FilterOperator.EQ,
                            userId
                        ) else null
                    )
                    ideaFlow.collect {
                        _ideasList.value = it.toMutableList()
                    }
                }
            }
        }
        catch (e: Exception) {
            Log.e("supabase", "subscribeToData: $e", )
        }
    }

    private suspend fun getUserIdAndRole() {
        try {
            userId = Constants.supabase.auth.currentUserOrNull()!!.id
            _userRole.value = Constants.supabase
                .from("users")
                .select(columns = Columns.list("role_id")) {
                    filter {
                        eq("id", userId)
                    }
                }.decodeSingle<RoleId>()
        }
        catch (e: Exception) {
            Log.e("supabase", "getUserIdAndRole: $e", )
        }
    }

    init {
        viewModelScope.launch {
            getImages()
        }
    }
}