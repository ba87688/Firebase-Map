package com.example2.roomapp.fragments.savingfragment

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import com.example2.roomapp.R
import com.example2.roomapp.data.Reminder
import com.example2.roomapp.data.database.RemindersDatabase
import com.example2.roomapp.databinding.FragmentSavingDetailsBinding
import com.example2.roomapp.viewmodels.login.LoginViewModel
import com.example2.roomapp.viewmodels.login.LoginViewModelFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SavingReminderFragment : Fragment() {

    private var _binding: FragmentSavingDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel

    val args: SavingReminderFragmentArgs by navArgs()

    @Inject
    lateinit var db:RemindersDatabase

    @Inject
    lateinit var application: Application


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSavingDetailsBinding.inflate(inflater, container, false)

        val viewModelFactory = LoginViewModelFactory(db, application,null,this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)


        val reminder = args.reminder

        binding.tvNameOfReminderLocationEdit.text = reminder.title.toString()



        binding.floatingActionButtonSave.setOnClickListener {
            if (binding.etReminderTitleEdit.text.isEmpty() || binding.etReminderDescriptionEdit.text.isEmpty()) {
                val snack = Snackbar.make(
                    requireView(),
                    "Please enter title and description.",
                    Snackbar.LENGTH_SHORT
                ).setAction("Action", null)
                snack.show()
            } else {
                val reminderToSave = Reminder(
                    binding.etReminderTitleEdit.text.toString(),
                    binding.etReminderDescriptionEdit.text.toString(),
                    reminder.title.toString(),
                    reminder.latitude,
                    reminder.longitude
                )

                viewModel.insertReminder(reminderToSave)

                findNavController().navigate(SavingReminderFragmentDirections.actionSavingReminderFragmentToRemindersFragment())

            }
        }

        return binding.root
    }


}