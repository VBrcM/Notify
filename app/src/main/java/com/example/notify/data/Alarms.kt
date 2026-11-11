package com.example.notify.data

data class Alarms(
    val alarmId: String,
    val title: String,
    val description: String,
    val hour: Int,
    val minute: Int,
    val endHour: Int,
    val endMinute: Int,
    val dateDisplay: String,
    val endDateDisplay: String,
    val dInMillis: Long,
    val endDInMillis: Long,
    val groupDisp: String,
    val currentGroupId: String,
    val intervalDays: Int,
    val intervalHours: Int,
    val intervalMinutes: Int,
    val intervalInMillis: Long,
    val startTimeInMillis: Long,
    val endTimeInMillis: Long
)