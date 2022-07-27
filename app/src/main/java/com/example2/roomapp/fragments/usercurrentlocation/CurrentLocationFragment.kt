package com.example2.roomapp.fragments.usercurrentlocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example2.roomapp.R
import com.example2.roomapp.data.Reminder
import com.example2.roomapp.databinding.FragmentCurrentLocationBinding
import com.example2.roomapp.databinding.FragmentRemindersBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.material.snackbar.Snackbar

class CurrentLocationFragment : Fragment() {

    private var _binding:FragmentCurrentLocationBinding?=null
    private val binding get() = _binding!!


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionCode = 2

    private val REQUEST_LOCATION_PERMISSION = 1
    private var listOfLatLong: MutableList<LatLng> = mutableListOf()
    private var listOfMarkers: MutableList<Marker> = mutableListOf()
    private var listOfPoi: MutableList<PointOfInterest> = mutableListOf()


    lateinit var map: GoogleMap

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        enableMyLocation()

        fusedLocationClient.lastLocation.addOnSuccessListener { location :Location?->
            if (location!=null){
                Log.i("TAG", "Location latitude is : ${location.latitude.toString()} ")
                Log.i("TAG", "Location latitude is : ${location.longitude.toString()} ")
                val sydney = LatLng(location.latitude,location.longitude)




//                googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 11f))

            }

        }


        setPoiClick(map)

//        val sydney = LatLng(-34.0, 151.0)
//        val sydney = latLng

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentCurrentLocationBinding.inflate(inflater,container,false)



        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity().application)


        binding.buttonMapSave.setOnClickListener {
            Log.i("TAG", "clicked on button ")

            if (listOfMarkers.isEmpty()){
                Log.i("TAG", "clicked on button in empty list ")
                val snack = Snackbar.make(requireView(),"Please pick a location to save.",Snackbar.LENGTH_SHORT).setAction("Action", null)
                snack.show()
            }
            else{
                Log.i("TAG", "LIST OF MARKERS: ${listOfMarkers.get(0)} ")
                val nav = findNavController()

                val currentPoi = listOfPoi.get(0)
                val reminder = Reminder(currentPoi.name,currentPoi.name,currentPoi.name,currentPoi.latLng.latitude.toString(),currentPoi.latLng.longitude.toString(),currentPoi.placeId)
//                listOfPoi.clear()
//                listOfMarkers.clear()
                nav.navigate(CurrentLocationFragmentDirections.actionCurrentLocationFragmentToSavingReminderFragment(reminder))

            }



        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)


    }


    //adding point of interest click listenener method
    private fun setPoiClick(map: GoogleMap){
        map.setOnPoiClickListener { poi->
            Log.i("TAG", "setPoiClick: ${poi.latLng}")

            //new marker
            val poiMarket = map.addMarker(
                MarkerOptions().position(poi.latLng).title(poi.name)
            )
            //if there is a marker already placed, remove it and add the new marker
            if (!(listOfMarkers.isEmpty())){
                val removedMarker = listOfMarkers.get(0)
                removedMarker.remove()
                listOfMarkers.removeAt(0)

                //remove poi
                listOfPoi.removeAt(0)
            }
            listOfMarkers.add(poiMarket!!)
            listOfPoi.add(poi)






        }
    }

    //end of adding point of interest click listenener method







    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            this@CurrentLocationFragment.requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED
    }
    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            map.setMyLocationEnabled(true)
            Log.i("TAG", "enableMyLocation: permission is granted")
        }
        else {
            ActivityCompat.requestPermissions(
                this@CurrentLocationFragment.requireActivity(),
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.size > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }
}