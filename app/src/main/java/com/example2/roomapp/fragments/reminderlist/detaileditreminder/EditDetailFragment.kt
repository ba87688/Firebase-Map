package com.example2.roomapp.fragments.reminderlist.detaileditreminder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example2.roomapp.R
import com.example2.roomapp.databinding.FragmentEditDetailBinding
import com.example2.roomapp.databinding.FragmentRemindersBinding


class EditDetailFragment : Fragment() {

    private var _binding: FragmentEditDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentEditDetailBinding.inflate(inflater,container,false)

        return binding.root
    }

}