package com.example.notify.alarmManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.notify.data.Alarms


class DefaultAlarmScheduler(
    private val context: Context
): AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    private val scheduledAlarmIds = mutableSetOf<Int>()

    override fun schedule(alarm: Alarms) {
        val alarmId = alarm.alarmId

        if (scheduledAlarmIds.contains(alarmId.hashCode())) {
            return
        }
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", alarm.alarmId)
            putExtra("EXTRA_TITLE", alarm.title)
            putExtra("EXTRA_DESCRIPTION", alarm.description)
            putExtra("EXTRA_GROUP", alarm.groupDisp)
            putExtra("EXTRA_END_DATE", alarm.endDateDisplay)
        }

        val currentTimeMillis = System.currentTimeMillis()

        // Calculate initial start time considering the current time
        var currentStartTime = if (currentTimeMillis > alarm.startTimeInMillis) {
            val intervalCount = (currentTimeMillis - alarm.startTimeInMillis) / alarm.intervalInMillis
            alarm.startTimeInMillis + (intervalCount + 1) * alarm.intervalInMillis // Next interval after current time
        } else {
            alarm.startTimeInMillis
        }

        while (currentStartTime < alarm.endTimeInMillis) {
            val requestCode = generateRequestCode(alarmId, currentStartTime)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    currentStartTime,
                    pendingIntent
                )
            }
            scheduledAlarmIds.add(requestCode)
            currentStartTime += alarm.intervalInMillis
        }

        if (currentStartTime <= alarm.endTimeInMillis) {
            val requestCode = generateRequestCode(alarmId, currentStartTime)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    alarm.endTimeInMillis,
                    pendingIntent
                )
            }
            scheduledAlarmIds.add(requestCode)
        }
    }

    override fun scheduleAlarms(alarms: List<Alarms>) {
        alarms.forEach { alarm ->
            schedule(alarm)
        }
    }

    override fun cancelAll() {
        scheduledAlarmIds.forEach { requestCode ->
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
        }
        scheduledAlarmIds.clear()
    }

    override fun cancel(alarm: Alarms) {
        val requestCode = generateRequestCode(alarm.alarmId, alarm.startTimeInMillis)
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
        scheduledAlarmIds.remove(requestCode)
    }

    private fun generateRequestCode(alarmId: String, startTime: Long): Int {
        // Generate a unique request code based on the alarm ID and start time
        return "${alarmId}_${startTime}".hashCode()
    }
}