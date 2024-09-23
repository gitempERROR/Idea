package com.example.idea.domain

import android.util.Log
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

    private var _ideasList : MutableStateFlow<MutableList<IdeaData>> = MutableStateFlow(mutableListOf())
    val ideasList: StateFlow<MutableList<IdeaData>> = _ideasList.asStateFlow()

    private var _editedIdea = mutableStateOf(IdeaData(ideasList.value.size,"", "", "", 1))
    val editedIdea = _editedIdea.value

    private var userRole: RoleId = RoleId(0)
    private var userId: String = ""

    fun navigateToLogin() {
        viewModelScope.launch {
            _navigationStateFlow.value = Routes.Login
        }
    }

    val bucket = Constants.supabase.storage.from("images")

    var icons: MutableList<String> = mutableListOf("", "", "")

    private fun getImages() {
        try {
            icons[0] = bucket.publicUrl("ongoing.png")
            icons[1] = bucket.publicUrl("refuse.png")
            icons[2] = bucket.publicUrl("accept.png")
            Log.d("", icons[0])
        }
        catch (e: Exception) {
            Log.e("img E", "getImages: $e", )
        }
    }

    init{
        viewModelScope.launch {
            getImages()
        }
    }

    fun subscribeToData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getUserIdAndRole()
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
                    Log.d("ideasList", it.size.toString())
                    _ideasList.value = it.toMutableList()
                }
            }
        }
    }

    suspend fun getUserIdAndRole() {
        userId = Constants.supabase.auth.currentUserOrNull()!!.id
        userRole = Constants.supabase
            .from("users")
            .select(columns = Columns.list("role_id")) {
                filter {
                    eq("id", userId)
                }
            }.decodeSingle<RoleId>()
    }

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