package com.receivers.broadcast

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class AirplaneModeService : Service() {

    private  var br: BroadCastExample= BroadCastExample()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // Register a BroadcastReceiver to listen to airplane mode changes
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        br = BroadCastExample()
        registerReceiver(br, filter)

        // Start the service as a foreground service
        val notification = createNotification()
        startForeground(1, notification)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the BroadcastReceiver
        unregisterReceiver(br)
    }

    private fun createNotification(): Notification {
        val channelId = "AirplaneModeServiceChannel"
        val channelName = "Airplane Mode Service Channel"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification channel (required for API 26+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // Create a notification for the foreground service
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Airplane Mode Service")
            .setContentText("Listening for airplane mode changes")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .build()
    }
}
