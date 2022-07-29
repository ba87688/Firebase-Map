package com.example2.roomapp.util.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build

val CHANNEL_ID = "channelID"
val NOTIFICATION_ID = 0
val CHANNEL_NAME = "channelname"

class NotificationChannel(val context: Context) {




    fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                    enableLights(true)
                    lightColor = Color.RED
                    enableVibration(true)
                    description = "Time for breakfast"

            }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)

        }
    }









}
