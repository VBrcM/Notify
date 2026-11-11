package com.example.notify.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notify.data.Groups
import com.example.notify.repositories.GroupRepository
import com.example.notify.uiState.JoinGroupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinGroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository
) : ViewModel() {

    private val _joinGroupState = MutableStateFlow<JoinGroupState>(JoinGroupState.Idle)
    val joinGroupState: StateFlow<JoinGroupState> = _joinGroupState

    fun joinGroup(groupId: String) {
        viewModelScope.launch {
            _joinGroupState.value = JoinGroupState.Loading
            try {
                groupRepository.joinGroup(groupId)
                _joinGroupState.value = JoinGroupState.Success
                _joinGroupState.value = JoinGroupState.NavigateToGroupScreen // Set navigation state
            } catch (e: Exception) {
                _joinGroupState.value = JoinGroupState.Error(e.message ?: "An error occurred")
            }
        }
    }

    private val _groupsLiveData = MutableLiveData<List<Groups>>()
    val groupsLiveData: LiveData<List<Groups>> get() = _groupsLiveData

    fun getGroupsForCurrentUser() {
        viewModelScope.launch {
            try {
                val groups = groupRepository.getGroupsForCurrentUser()
                _groupsLiveData.value = groups
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}