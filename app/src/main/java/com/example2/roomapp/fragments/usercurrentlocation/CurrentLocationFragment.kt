package com.example2.roomapp.fragments.usercurrentlocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example2.roomapp.R
import com.example2.roomapp.data.Reminder
import com.example2.roomapp.databinding.FragmentCurrentLocationBinding
import com.example2.roomapp.databinding.FragmentRemindersBinding
import com.example2.roomapp.geofence.GeofenceHelper
import com.example2.roomapp.viewmodels.reminderlocation.LocationViewModel
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar

class CurrentLocationFragment : Fragment(), GoogleMap.OnMapLongClickListener {

    private var _binding: FragmentCurrentLocationBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LocationViewModel

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionCode = 2

    private val REQUEST_LOCATION_PERMISSION = 1

    //geofence variables
    private lateinit var geofencingClient: GeofencingClient
    private val RADIUS_OF_CIRCULE = 2000
    private val GEOFENCE_ID = "SOME_GEOFENCE_ID"
    private lateinit var geofenceHelper: GeofenceHelper

    var mapCircle: Circle? = null


    lateinit var map: GoogleMap

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.clear()
        map = googleMap

        enableMyLocation()



//        initialize geofencingclient
        geofencingClient = LocationServices.getGeofencingClient(requireContext())
        geofenceHelper = GeofenceHelper(requireContext())

        googleMap.setOnMapLongClickListener(this)


        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->

            val sydney = LatLng(37.49, -122.2555)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 11f))

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 11f))
        }

        setPoiClick(map)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrentLocationBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LocationViewModel::class.java)


        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity().application)

        binding.buttonMapSave.setOnClickListener {
            if (viewModel.listOfMarkers.isEmpty()) {
                val snack = Snackbar.make(
                    requireView(),
                    "Please pick a location to save.",
                    Snackbar.LENGTH_SHORT
                ).setAction("Action", null)
                snack.show()
            } else {
                val nav = findNavController()

                val currentPoi = viewModel.listOfPoi.get(0)
                val reminder = viewModel.createReminder(currentPoi)

                val ltlong = viewModel.getLatLong(currentPoi)
                addGeofence(ltlong, RADIUS_OF_CIRCULE.toFloat(), currentPoi.placeId)

                nav.navigate(
                    CurrentLocationFragmentDirections.actionCurrentLocationFragmentToSavingReminderFragment(
                        reminder
                    )
                )

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
    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            Log.i("TAG", "setPoiClick: ${poi.latLng}")

            map.clear()
            //new marker
            val poiMarket = map.addMarker(
                MarkerOptions().position(poi.latLng).title(poi.name)
            )

            //add circle
            addCircle(poi.latLng, RADIUS_OF_CIRCULE.toDouble())

            //if there is a marker already placed, remove it and add the new marker
            if (!(viewModel.listOfMarkers.isEmpty())) {
                val removedMarker = viewModel.listOfMarkers.get(0)
                removedMarker.remove()
                viewModel.listOfMarkers.removeAt(0)

                //remove poi
                viewModel.listOfPoi.removeAt(0)
            }
            viewModel.listOfMarkers.add(poiMarket!!)
            viewModel.listOfPoi.add(poi)


        }
    }

    //end of adding point of interest click listenener method


    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this@CurrentLocationFragment.requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) === PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            map.setMyLocationEnabled(true)
            Log.i("TAG", "enableMyLocation: permission is granted")
        } else {
            ActivityCompat.requestPermissions(
                this@CurrentLocationFragment.requireActivity(),
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
            ActivityCompat.requestPermissions(
                this@CurrentLocationFragment.requireActivity(),
                arrayOf<String>(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.size > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }


    override fun onMapLongClick(p0: LatLng) {

    }

    private fun addMarket(latLng: LatLng) {
        val markerOption = MarkerOptions().position(latLng)
        map.addMarker(markerOption)
    }

    private fun addCircle(latLng: LatLng, radius: Double) {
        val circleOptions = viewModel.getCircleOptions(latLng,radius)
        map.addCircle(circleOptions)

    }

    @SuppressLint("MissingPermission")
    private fun addGeofence(latLng: LatLng, radius: Float, id: String) {

        val geofence =
            geofenceHelper.getGeofence(id, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER)
        val geofenceRequest = geofenceHelper.getGeoFencingRequest(geofence!!)
        val pendingInt = geofenceHelper.getPendingIntent()

        geofencingClient.addGeofences(geofenceRequest, pendingInt)
            .addOnSuccessListener(OnSuccessListener {
                Log.i("TAG", "Geofence success: ")
            })
            .addOnFailureListener(OnFailureListener {
                Log.i("TAG", "Geofence failure: ")
            })
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {

        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


}