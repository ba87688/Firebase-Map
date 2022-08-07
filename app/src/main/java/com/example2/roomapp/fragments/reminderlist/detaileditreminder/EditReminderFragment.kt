package com.example2.roomapp.fragments.reminderlist.detaileditreminder

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example2.roomapp.data.database.RemindersDatabase
import com.example2.roomapp.databinding.FragmentEditReminderBinding
import com.example2.roomapp.geofence.GeofenceHelper
import com.example2.roomapp.viewmodels.login.LoginViewModel
import com.example2.roomapp.viewmodels.login.LoginViewModelFactory
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditReminderFragment : Fragment() {
    private var _binding: FragmentEditReminderBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel

    val args: EditReminderFragmentArgs by navArgs()


    @Inject
    lateinit var db:RemindersDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditReminderBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
//        val dataSource = RemindersDatabase.getDatabase(application)
        val viewModelFactory = LoginViewModelFactory(db, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        val reminder = args.reminderToEdit

        binding.editTitleReminder.text = reminder.title
        binding.editReminderDescription.text = reminder.description
        binding.editPlaceOfReminder.text = reminder.location

        binding.faoSaveReminderAgain.setOnClickListener {
            viewModel.deleteReminder(reminder)

            val geofencingClient = LocationServices.getGeofencingClient(requireContext())
            removeGeofences(geofencingClient)
            var nav = findNavController()
            nav.navigate(EditReminderFragmentDirections.actionEditReminderFragmentToRemindersFragment())
        }




        return binding.root
    }
    private fun removeGeofences(geofencingClient: GeofencingClient) {



        geofencingClient.removeGeofences(GeofenceHelper(requireContext()).getPendingIntent())?.run {
            addOnSuccessListener {
                Log.d("TAG", "YOOOOOOOOOOO Removed the geofence")


            }
            addOnFailureListener {
                Log.d("TAG", "YOOOOOOOOOOOO Removed geofence failed")
            }
        }
    }

}