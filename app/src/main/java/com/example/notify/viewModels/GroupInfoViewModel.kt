package com.example.notify.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.notify.data.Groups
import com.example.notify.navigation.Screens
import com.example.notify.repositories.GroupRepository
import com.example.notify.uiState.LeaveGroupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupInfoViewModel @Inject constructor(
    private val groupRepository: GroupRepository
) : ViewModel() {
    private val _groupDetails = MutableLiveData<Groups>()
    val groupDetails: LiveData<Groups> get() = _groupDetails

    private val _leaveGroupState = MutableLiveData<LeaveGroupState>()
    val leaveGroupState: LiveData<LeaveGroupState> get() = _leaveGroupState

    fun getGroupDetails(groupId: String) {
        viewModelScope.launch {
            try {
                val group = groupRepository.getGroupDetails(groupId)
                _groupDetails.value = group
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun leaveGroup(groupId: String, navController: NavController) {
        _leaveGroupState.value = LeaveGroupState.Loading
        viewModelScope.launch {
            try {
                groupRepository.leaveGroup(groupId)
                navController.navigate(Screens.ScreenGroupRoute.route)
            } catch (e: Exception) {
            } finally {
                _leaveGroupState.value = LeaveGroupState.Idle
            }
        }
    }
}