package com.example2.roomapp.fragments.main

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example2.roomapp.R
import com.example2.roomapp.data.Reminder
import com.example2.roomapp.data.database.RemindersDatabase
import com.example2.roomapp.databinding.FragmentRemindersBinding
import kotlinx.coroutines.launch

class RemindersFragment : Fragment() {
    private var _binding: FragmentRemindersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentRemindersBinding.inflate(inflater,container,false)




        val db = Room.databaseBuilder(activity?.applicationContext!!,RemindersDatabase::class.java,"reminders_database").allowMainThreadQueries().build()

        lifecycleScope.launch {
            db.reminderDao().insertReminder(Reminder("Hi","desceibe","er","33","rr","t"))
            val r = db.reminderDao().getReminderById("t")
            Log.i("TAG", "onCreateView: $r")
        }

        return binding.root
    }


}