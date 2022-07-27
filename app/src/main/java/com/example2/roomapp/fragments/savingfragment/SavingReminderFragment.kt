package com.example2.roomapp.fragments.savingfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example2.roomapp.R
import com.example2.roomapp.databinding.FragmentSavingReminderObjectBinding


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
        return binding.root
    }


}