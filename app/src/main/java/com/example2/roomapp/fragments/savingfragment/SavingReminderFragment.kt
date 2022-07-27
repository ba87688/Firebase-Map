package com.example2.roomapp.fragments.savingfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example2.roomapp.R
import com.example2.roomapp.databinding.FragmentRemindersBinding
import com.example2.roomapp.databinding.FragmentSavingReminderBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SavingReminderFragment : Fragment() {

    private var _binding:FragmentSavingReminderBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentSavingReminderBinding.inflate(inflater,container,false)

        // Inflate the layout for this fragment
        return binding.root
    }


}