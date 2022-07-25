package com.example2.roomapp.fragments.main

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example2.roomapp.R
import com.example2.roomapp.data.Reminder
import com.example2.roomapp.data.database.RemindersDatabase
import com.example2.roomapp.databinding.FragmentRemindersBinding
import com.example2.roomapp.fragments.login.LoginFragmentDirections
import com.example2.roomapp.viewmodels.login.LoginViewModel
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.launch

class RemindersFragment : Fragment() {
    private var _binding: FragmentRemindersBinding? = null
    private val binding get() = _binding!!
    private val viewModel : LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.logout_menu_item){
            Log.i("TAG", "onOptionsItemSelected: before he hit the ground")
            AuthUI.getInstance().signOut(requireContext())
            viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticateState->
                when(authenticateState){
                    LoginViewModel.AuthenticationState.AUTHENTICATED->{
                        Log.i("TAG", "onOptionsItemSelected: dude is auth")
                    }
                    LoginViewModel.AuthenticationState.UNAUTHENTICATED->{
//                        findNavController().navigate(R.id.loginFragment)
                        findNavController().navigate(RemindersFragmentDirections.actionRemindersFragmentToLoginFragment())


                    }

                }

            })
        }
        return super.onOptionsItemSelected(item)
    }

}