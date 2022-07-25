package com.example2.roomapp.fragments.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example2.roomapp.R
import com.example2.roomapp.databinding.FragmentLoginBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {
    private var _binding:FragmentLoginBinding? = null
    private val binding get() = _binding!!
    val SIGN_IN_REQUEST_CODE = -1


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
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build()
            )

            //create and lanuch sign in intent
            //listen to response of this activity
            //sign in code
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                SIGN_IN_REQUEST_CODE
            )

        }



        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == SIGN_IN_REQUEST_CODE){
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK){
                Log.i("TAG", "onActivityResult: successful ${FirebaseAuth.getInstance().currentUser?.displayName}")
            }
            else{

                Log.i("TAG", "Sign in unsuccessful ${response?.error?.errorCode}")

            }

        }
    }


}