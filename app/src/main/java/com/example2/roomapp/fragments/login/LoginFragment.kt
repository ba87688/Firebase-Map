package com.example2.roomapp.fragments.login

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example2.roomapp.R
import com.example2.roomapp.data.database.RemindersDatabase
import com.example2.roomapp.databinding.FragmentLoginBinding
import com.example2.roomapp.viewmodels.login.LoginViewModel
import com.example2.roomapp.viewmodels.login.LoginViewModelFactory
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    val SIGN_IN_REQUEST_CODE = -1

    private lateinit var viewModel: LoginViewModel
    @Inject
    lateinit var db:RemindersDatabase
    @Inject
    lateinit var application: Application

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)


        val viewModelFactory = LoginViewModelFactory ( db, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)




        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticateState ->
            when (authenticateState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    findNavController().navigate(R.id.remindersFragment)

                }
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> {
                    Log.i("BE WIFEY", "onCreateView: he is not logged in yet")
                }
            }

        })

        binding.buttonLogin.setOnClickListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build()
            )

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
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                findNavController().navigate(R.id.remindersFragment)
            } else {

                Log.i("TAG", "Sign in unsuccessful ${response?.error?.errorCode}")

            }

        }
    }

    private fun observeAuthenticationState() {

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {

                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    Log.i("TAG", "observeAuthenticationState: THE STATE")
                    val name = FirebaseAuth.getInstance().currentUser?.displayName
                    Log.i("TAG", "observeAuthenticationState: THE STATE $name")
                    binding.buttonLogin.text = "logout"
                    binding.buttonLogin.setOnClickListener {
                        AuthUI.getInstance().signOut(requireContext())
                    }
                }
                else -> {

                    //not logged in
                    //set text of button,on click
                }

            }

        })
    }

}