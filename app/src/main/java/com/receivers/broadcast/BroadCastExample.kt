package com.receivers.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BroadCastExample : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirPlane: Boolean = intent!!.getBooleanExtra("state", false)
        if (isAirPlane) {
            Toast.makeText(context, "This device is in the AirPlane mode.", Toast.LENGTH_SHORT)
                .show()
            val serviceIntent = Intent(context, AirplaneModeService::class.java)
            context?.startService(serviceIntent)
        } else {
            Toast.makeText(context, "This device is not in the AirPlane mode.", Toast.LENGTH_SHORT)
                .show()
            // Stop the service
            val serviceIntent = Intent(context, AirplaneModeService::class.java)
            context?.stopService(serviceIntent)
        }
    }
}