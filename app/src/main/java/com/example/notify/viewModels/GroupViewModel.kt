package com.example.notify.viewModels



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notify.data.Groups
import com.example.notify.repositories.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository
) : ViewModel() {

    private val _groupsLiveData = MutableLiveData<List<Groups>>()
    val groupsLiveData: LiveData<List<Groups>> get() = _groupsLiveData

    init {
        getGroupsForCurrentUser()
    }

    private fun getGroupsForCurrentUser() {
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