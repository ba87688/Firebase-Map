package com.example2.roomapp.fragments.login

import android.app.Activity
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


class LoginFragment : Fragment() {
    private var _binding:FragmentLoginBinding? = null
    private val binding get() = _binding!!
    val SIGN_IN_REQUEST_CODE = -1


//    private val viewModel : LoginViewModel by viewModels()

    private lateinit var viewModel:LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)

        val application = requireNotNull(this.activity).application
        val dataSource = RemindersDatabase.getDatabase(application)
        val viewModelFactory = LoginViewModelFactory(dataSource,application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
//        val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)






        val nav = findNavController()

        isUserLoggedIn()

        observeAuthenticationState()
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

//        viewModel.restaurants.observe(viewLifecycleOwner, Observer { obser->
//            Log.i("TAG", "onCreateView: creating things ${obser.id} ")
//        })


        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == SIGN_IN_REQUEST_CODE){
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK){
                Log.i("TAG", "onActivityResult: successful ${FirebaseAuth.getInstance().currentUser?.displayName}")
//                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRemindersFragment())
                findNavController().navigate(R.id.remindersFragment)
            }
            else{

                Log.i("TAG", "Sign in unsuccessful ${response?.error?.errorCode}")

            }

        }
    }

    private fun isUserLoggedIn(){

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticateState->
            when(authenticateState){
                LoginViewModel.AuthenticationState.AUTHENTICATED->{
                    findNavController().navigate(R.id.remindersFragment)

                }
            }

        })
    }


    private fun observeAuthenticationState(){
//        val factToDisplay = viewModel.getFactToDisplay(requireContext())

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when(authenticationState){

                LoginViewModel.AuthenticationState.AUTHENTICATED->{
                    Log.i("TAG", "observeAuthenticationState: THE STATE")
                    val name =  FirebaseAuth.getInstance().currentUser?.displayName
                    Log.i("TAG", "observeAuthenticationState: THE STATE $name")
                    binding.buttonLogin.text ="logout"
                    binding.buttonLogin.setOnClickListener {
                        AuthUI.getInstance().signOut(requireContext())
                    }
                }
                else ->{

                    //not logged in
                    //set text of button,on click
                }

            }

        })
    }

}