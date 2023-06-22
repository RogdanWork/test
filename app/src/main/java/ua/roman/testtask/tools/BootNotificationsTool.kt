package ua.roman.testtask.tools

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import ua.roman.testtask.R

class BootNotificationsTool(private val context: Context) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
    }

    fun showBootNotification(message: String) {
        createNotificationChannel()

        val notification = NotificationCompat.Builder(context, context.getString(R.string.channel_id))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.boot_worker_notification_title))
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setOngoing(true)
            .build()

        notificationManager.notify(BOOT_NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                context.getString(R.string.channel_id),
                context.getString(R.string.channel_name), importance
            ).apply {
                description = context.getString(R.string.channel_description)
            }

            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val BOOT_NOTIFICATION_ID = 1996
    }
}