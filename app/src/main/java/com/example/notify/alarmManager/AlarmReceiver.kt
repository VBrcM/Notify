package com.example.notify.alarmManager

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.notify.R


class AlarmReceiver : BroadcastReceiver(
) {
    override fun onReceive(context: Context, intent: Intent) {
        notificationBuilder(context, intent)
    }

    private fun notificationBuilder(context: Context, intent: Intent){
        val alarmId = intent.getStringExtra("EXTRA_MESSAGE")
        val alarmTitle = intent.getStringExtra("EXTRA_TITLE")
        val alarmDescription = intent.getStringExtra("EXTRA_DESCRIPTION")
        val alarmGroup = intent.getStringExtra("EXTRA_GROUP")
        val alarmEndDAte = intent.getStringExtra("EXTRA_END_DATE")
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("$alarmTitle ending on $alarmEndDAte")
            .setContentText("$alarmDescription")
            .setGroup(alarmGroup)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(alarmId.hashCode(), notification)
    }
    companion object {
        const val CHANNEL_ID = "alarm_channel"
        const val NOTIFICATION_ID = 1
    }
}