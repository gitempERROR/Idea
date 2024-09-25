package com.example.idea.domain

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Constraints
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idea.domain.utils.Constants
import com.example.idea.model.IdeaData
import com.example.idea.model.RoleId
import com.example.idea.view.MainActivity.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class IdeaViewModel @Inject constructor() : ViewModel() {

    private val bucket = Constants.supabase.storage.from("images")

    var icons: MutableList<String> = mutableListOf("", "", "")

    init {
        getImages()
    }

    private val _navigationStateFlow : MutableStateFlow<Routes?> = MutableStateFlow(null)
    val navigationStateFlow : StateFlow<Routes?> = _navigationStateFlow.asStateFlow()

    private var _userRole: MutableState<RoleId> = mutableStateOf(RoleId(0))
    val userRole by _userRole

    fun navigateToMain() {
        viewModelScope.launch {
            _navigationStateFlow.value = Routes.Main
        }
    }

    fun updateIdeaStatus(newStatus: Int, ideaID: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Constants.supabase.from("ideas").update(
                    {
                        IdeaData::status setTo newStatus
                    }
                ) {
                    filter {
                        IdeaData::id eq ideaID.toInt()
                    }
                }
            }
        }
    }

    private fun getImages() {
        try {
            icons[0] = bucket.publicUrl("ongoing.png")
            icons[1] = bucket.publicUrl("refuse.png")
            icons[2] = bucket.publicUrl("accept.png")
            Log.d("", icons[0])
        }
        catch (e: Exception) {
            Log.e("supabase", "getImages: $e", )
        }
    }

    fun getUserRole() {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val userId = Constants.supabase.auth.currentUserOrNull()!!.id
                    _userRole.value = Constants.supabase
                        .from("users")
                        .select(columns = Columns.list("role_id")) {
                            filter {
                                eq("id", userId)
                            }
                        }.decodeSingle<RoleId>()
                }
            }
        }
        catch (e: Exception) {
            Log.e("supabase", "getUserRole: $e", )
        }
    }
}