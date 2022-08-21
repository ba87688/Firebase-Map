package com.example2.roomapp.viewmodels.reminderlocation

import android.app.Activity
import android.graphics.Color
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example2.roomapp.data.Reminder
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PointOfInterest
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {
    lateinit var geofencingClient: GeofencingClient

    var listOfMarkers: MutableList<Marker> = mutableListOf()
    var listOfPoi: MutableList<PointOfInterest> = mutableListOf()

    var number:String? =null

    init {
        number = "EVan"

        viewModelScope.launch {

        }
    }

    fun getCircleOptions(latLng:LatLng,radius:Double):CircleOptions {
        val circleOptions = CircleOptions()
        circleOptions.center(latLng)
        circleOptions.radius(radius.toDouble())
        circleOptions.strokeColor(Color.argb(255, 255, 0, 0))
        circleOptions.fillColor(Color.argb(66, 255, 0, 0))
        circleOptions.strokeWidth(4F)
        return circleOptions
    }

    fun createReminder(currentPoi: PointOfInterest):Reminder{

        val reminder = Reminder(
            currentPoi.name,
            currentPoi.name,
            currentPoi.name,
            currentPoi.latLng.latitude.toString(),
            currentPoi.latLng.longitude.toString(),
            currentPoi.placeId
        )
        return reminder

    }

    fun getLatLong(currentPoi: PointOfInterest): LatLng =LatLng(currentPoi.latLng.latitude, currentPoi.latLng.longitude)



}