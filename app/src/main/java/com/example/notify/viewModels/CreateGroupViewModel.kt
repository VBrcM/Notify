package com.example.notify.viewModels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notify.data.Groups
import com.example.notify.repositories.GroupRepository
import com.example.notify.uiState.GroupCreateState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository
) : ViewModel() {

    private val _createGroupState = MutableStateFlow<GroupCreateState?>(null)
    val createGroupState: StateFlow<GroupCreateState?> = _createGroupState

    fun createGroup(groupName: String) {
        viewModelScope.launch {
            try {
                val currentUser = FirebaseAuth.getInstance().currentUser
                val currentUserEmail = currentUser?.email
                if (currentUserEmail != null) {
                    val newGroup = Groups(
                        creatorEmail = currentUserEmail,
                        members = listOf(currentUserEmail),
                        groupName = groupName,
                        memberCount = 1,
                        creatorUsername = currentUser.displayName ?: "",
                        adminEmails = listOf(currentUserEmail),
                        alarms = listOf<String>()
                    )
                    val groupId = groupRepository.createGroup(newGroup)
                    _createGroupState.value = GroupCreateState.Success(groupId ?: "")
                } else {
                    _createGroupState.value = GroupCreateState.Error("User email not available")
                }
            } catch (e: Exception) {
                _createGroupState.value = GroupCreateState.Error(e.message ?: "Unknown error")
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