package com.example2.roomapp.fragments.savingfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import com.example2.roomapp.R
import com.example2.roomapp.data.Reminder
import com.example2.roomapp.data.database.RemindersDatabase
import com.example2.roomapp.databinding.FragmentSavingReminderObjectBinding
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class SavingReminderFragment : Fragment() {

    private var _binding:FragmentSavingReminderObjectBinding?= null
    private val binding get() = _binding!!

    val args: SavingReminderFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentSavingReminderObjectBinding.inflate(inflater,container,false)


        val reminder = args.reminder
        Log.i("TAG", "REMINDER IS : ${reminder.latitude} ")
        // Inflate the layout for this fragment

//        val geo = LatLng(reminder.latitude?.toDouble()!!, reminder.longitude?.toDouble()!!)
        binding.tvNameOfReminderLocation.text = reminder.title.toString()
        binding.tvTitleOfReminder.text = "Reminder Location"



        binding.floatingActionButtonSave.setOnClickListener {
//            if (binding.etReminderTitle.text.isNotEmpty())
            if (binding.etReminderTitle.text.isEmpty() || binding.etReminderDescription.text.isEmpty()){
                val snack = Snackbar.make(requireView(),"Please enter title and description.", Snackbar.LENGTH_SHORT).setAction("Action", null)
                snack.show()
            }else{
                val reminderToSave = Reminder(binding.etReminderTitle.text.toString(),binding.etReminderDescription.text.toString(),reminder.title.toString(),reminder.latitude,reminder.longitude)
                val db = Room.databaseBuilder(activity?.applicationContext!!,
                    RemindersDatabase::class.java,"reminders_database").allowMainThreadQueries().build()
                lifecycleScope.launch {

                    db.reminderDao().insertReminder(reminderToSave)

                    Log.i("TAG", "onCreateView: SUCCESSFUL NINJA")
                }



            }
        }

        return binding.root
    }


}