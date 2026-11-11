package com.example.notify.viewModels



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notify.data.AlarmGroup
import com.example.notify.data.Alarms
import com.example.notify.repositories.AlarmRepository
import com.example.notify.uiState.GroupsResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddAlarmViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository
) : ViewModel() {

    private val _alarms = MutableLiveData<List<Alarms>>()
    val alarms: LiveData<List<Alarms>> = _alarms

    private val _groupsResult = MutableLiveData<GroupsResult>()
    val groupsResult: LiveData<GroupsResult>
        get() = _groupsResult

    fun fetchGroupsForUser() {
        viewModelScope.launch {
            _groupsResult.value = GroupsResult.Loading
            val result = withContext(Dispatchers.IO) {
                alarmRepository.getGroupsForUser()
            }
            _groupsResult.value = result
        }
    }
    fun saveAlarm(alarms: Alarms, selectedGroup: AlarmGroup?) {
        viewModelScope.launch {
            selectedGroup?.let { group ->
                val selectedGroupId = group.id
                alarmRepository.saveAlarm(alarms, selectedGroupId)
            }
        }
    }
}