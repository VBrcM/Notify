package com.example.notify.alarmManager

import com.example.notify.data.Alarms

interface AlarmScheduler {

    fun cancel(alarm: Alarms)
    fun schedule(alarm: Alarms)
    fun scheduleAlarms(alarms: List<Alarms>)
    fun cancelAll()
}