package com.example2.roomapp.geofence

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example2.roomapp.R
import com.example2.roomapp.util.notifications.CHANNEL_ID
import com.example2.roomapp.util.notifications.NOTIFICATION_ID
import com.example2.roomapp.util.notifications.NotificationChannel
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

            // This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        val app = context

        val geofencer = GeofencingEvent.fromIntent(intent)
        if (geofencer.hasError()){
            Log.i(TAG, "onReceive: error receiving geofence event...")
            return
        }
        val geofenceList = geofencer.triggeringGeofences
        val transitionType = geofencer.geofenceTransition

        when(transitionType){
            Geofence.GEOFENCE_TRANSITION_ENTER -> Log.i(TAG, "onReceive: YOU FREAKIN ENTERED") 
            Geofence.GEOFENCE_TRANSITION_EXIT -> Log.i(TAG, "onReceive: YOU FREAKIN EXITED")
            else -> Log.i(TAG, "onReceive: ")

        }

        val notice = NotificationChannel(context)
        //create a channel. only needed once
        notice.createNotificationChannel()

        if (intent.action.toString() == "PendingIntent.FLAG_UPDATE_CURRENT"){
            //now create a notification taht goes in the channel
            val notification = NotificationCompat.Builder(context,CHANNEL_ID)
                .setContentTitle("Awesome, you entered a zone")
                .setContentText("This is exciting!")
                .setSmallIcon(R.drawable.ic_save)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            val notificationManager = NotificationManagerCompat.from(context)

            notificationManager.notify(NOTIFICATION_ID,notification)
        }






    }



}