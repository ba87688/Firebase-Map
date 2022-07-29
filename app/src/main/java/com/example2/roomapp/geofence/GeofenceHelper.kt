package com.example2.roomapp.geofence

import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.maps.model.LatLng

const val TAG = "GEOFENCE_HELPER"
class GeofenceHelper(context:Context): ContextWrapper(context) {

    var pendingIntenta:PendingIntent? = null

    public fun getGeoFencingRequest(geofence:Geofence):GeofencingRequest?{
        return GeofencingRequest.Builder()
            .addGeofence(geofence)
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .build()
    }



    public fun getGeofence(ID:String,latLng: LatLng,radius:Float,transitionType:Int):Geofence?{
        return Geofence.Builder()
            .setCircularRegion(latLng.latitude,latLng.longitude,radius)
            .setRequestId(ID)
            .setTransitionTypes(transitionType)
            .setLoiteringDelay(500)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()
    }





    public fun getPendingIntent():PendingIntent?{
        if(pendingIntenta!=null){
            Log.i(TAG, "getPendingIntent: error")
            return pendingIntenta
        }
        val geofencingPendingIntnet:PendingIntent by lazy {
            val intent = Intent(this,GeofenceBroadcastReceiver::class.java)
            intent.action = "PendingIntent.FLAG_UPDATE_CURRENT"
            PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        }
//        val intent = Intent(this,GeofenceBroadcastReceiver::class.java)

//
//        pendingIntenta = PendingIntent.getBroadcast(this,2607,intent,PendingIntent.FLAG_UPDATE_CURRENT)
//        return pendingIntenta
return geofencingPendingIntnet

    }


}