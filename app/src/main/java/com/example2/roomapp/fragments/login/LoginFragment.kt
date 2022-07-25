package com.example2.roomapp.fragments.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example2.roomapp.R
import com.example2.roomapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var _binding:FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)


        binding.buttonLogin.setOnClickListener {
            Log.i("TAG", "onCreateView: Clicked on a button")
            //give users sign in ability with email or gmail.
            //if user choose email, they need a password too
//            val providers =  arrayListOf(
//                AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()

        }

        return binding.root
    }


}