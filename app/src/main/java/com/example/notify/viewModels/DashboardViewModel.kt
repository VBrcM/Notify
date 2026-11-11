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
class DashboardViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {

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
}