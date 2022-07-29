package com.example2.roomapp.fragments.savingfragment

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
import com.example2.roomapp.databinding.FragmentSavingReminderObjectBinding
import com.example2.roomapp.viewmodels.login.LoginViewModel
import com.example2.roomapp.viewmodels.login.LoginViewModelFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class SavingReminderFragment : Fragment() {

    private var _binding:FragmentSavingReminderObjectBinding?= null
    private val binding get() = _binding!!


    private lateinit var viewModel: LoginViewModel


    val args: SavingReminderFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentSavingReminderObjectBinding.inflate(inflater,container,false)

        val application = requireNotNull(this.activity).application
        val dataSource = RemindersDatabase.getDatabase(application)
        val viewModelFactory = LoginViewModelFactory(dataSource,application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)


        val reminder = args.reminder
        Log.i("TAG", "REMINDER IS : ${reminder.latitude} ")
        // Inflate the layout for this fragment

//        val geo = LatLng(reminder.latitude?.toDouble()!!, reminder.longitude?.toDouble()!!)
        binding.tvNameOfReminderLocationEdit.text = reminder.title.toString()
        binding.tvTitleOfReminderEdit.text = "Reminder Location"



        binding.floatingActionButtonSave.setOnClickListener {
//            if (binding.etReminderTitle.text.isNotEmpty())
            if (binding.etReminderTitleEdit.text.isEmpty() || binding.etReminderDescriptionEdit.text.isEmpty()){
                val snack = Snackbar.make(requireView(),"Please enter title and description.", Snackbar.LENGTH_SHORT).setAction("Action", null)
                snack.show()
            }else{
                val reminderToSave = Reminder(binding.etReminderTitleEdit.text.toString(),binding.etReminderDescriptionEdit.text.toString(),reminder.title.toString(),reminder.latitude,reminder.longitude)

                viewModel.insertReminder(reminderToSave)

                findNavController().navigate(SavingReminderFragmentDirections.actionSavingReminderFragmentToRemindersFragment())

            }
        }

        return binding.root
    }


}