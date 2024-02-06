package com.example.spelltester

import android.app.*
import android.content.*
import android.os.*
import com.example.spelltester.ui.*

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
    createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(ReminderNotification.id,
                ReminderNotification.name,
                ReminderNotification.importance).apply {
                description = ReminderNotification.description
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}