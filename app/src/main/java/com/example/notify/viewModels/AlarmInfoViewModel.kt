package com.example.notify.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notify.alarmManager.AlarmScheduler
import com.example.notify.data.Alarms
import com.example.notify.repositories.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmInfoViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {
    private val _alarm = MutableLiveData<Alarms?>()
    val alarm: LiveData<Alarms?> = _alarm

    private val _isAdmin = MutableLiveData<Boolean>()
    val isAdmin: LiveData<Boolean> = _isAdmin

    fun getAlarmById(currentGroupId: String, alarmId: String) {
        viewModelScope.launch {
            val alarm = alarmRepository.getAlarmById(currentGroupId, alarmId)
            _alarm.value = alarm

            val isAdmin = alarmRepository.isUserAdmin(currentGroupId)
            _isAdmin.value = isAdmin
        }
    }

    private val _alarms = MutableLiveData<List<Alarms>>()
    val alarms: LiveData<List<Alarms>> = _alarms
    fun fetchAlarms() {
        viewModelScope.launch {
            try {
                val fetchedAlarms = alarmRepository.getAlarmsForUser()
                _alarms.value = fetchedAlarms

                alarmScheduler.cancelAll()
                alarmScheduler.scheduleAlarms(fetchedAlarms)
            } catch (e: Exception) {
                // Handle errors or log them
            }
        }
    }
    fun deleteAlarm(alarmId: String, groupId: String) {
        viewModelScope.launch {
            alarmRepository.deleteAlarm(alarmId, groupId)
        }
    }
}