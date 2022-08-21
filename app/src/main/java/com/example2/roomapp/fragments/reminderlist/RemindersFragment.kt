package com.example2.roomapp.fragments.reminderlist

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.Gravity.apply
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example2.roomapp.R
import com.example2.roomapp.adapter.RemainderRecyclerViewAdapter
import com.example2.roomapp.data.Reminder
import com.example2.roomapp.data.database.RemindersDatabase
import com.example2.roomapp.databinding.FragmentRemindersBinding
import com.example2.roomapp.fragments.savingfragment.SavingReminderFragmentDirections
import com.example2.roomapp.viewmodels.login.LoginViewModel
import com.example2.roomapp.viewmodels.login.LoginViewModelFactory
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.launch
import android.view.Gravity
import com.example2.roomapp.other.Constants.REQUEST_LOCATION_PERMISSION
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RemindersFragment : Fragment(), RemainderRecyclerViewAdapter.OnItemClickListener {
    private var _binding: FragmentRemindersBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel

    @Inject
    lateinit var datasource: RemindersDatabase

    @Inject
    lateinit var application: Application

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRemindersBinding.inflate(inflater, container, false)


        val viewModelFactory = LoginViewModelFactory(datasource, application,null,this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)


        enableMyLocation()
        enableMyLocation2()

        binding.floatingActionButton.setOnClickListener {
            val controller = findNavController()
            controller.navigate(RemindersFragmentDirections.actionRemindersFragmentToCurrentlocationfragment())

        }

        viewModel.restaurants.observe(viewLifecycleOwner, Observer { it ->
            viewModel.list2 = it.data!!
            val list = it.data
            if (list != null) {
                binding.reminderRecyclerView.adapter =
                    RemainderRecyclerViewAdapter(list, this@RemindersFragment)
            }
            if (list?.size == 0 || list == null) {
                    binding.imagetry.setImageResource(R.drawable.ic_no_data)
                    binding.reminderRecyclerView.visibility = View.INVISIBLE
            }
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.logout_menu_item) {
            AuthUI.getInstance().signOut(requireContext())

            viewModel.authenticationState.observe(
                viewLifecycleOwner,
                Observer { authenticateState ->
                    when (authenticateState) {
                        LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                        }
                        LoginViewModel.AuthenticationState.UNAUTHENTICATED -> {
                            findNavController().navigate(RemindersFragmentDirections.actionRemindersFragmentToLoginFragment())
                        }

                    }

                })
        }
        return super.onOptionsItemSelected(item)
    }


    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this@RemindersFragment.requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) === PackageManager.PERMISSION_GRANTED
//                &&
//                ContextCompat.checkSelfPermission(
//                    this@RemindersFragment.requireContext(),
//                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
//                ) === PackageManager.PERMISSION_GRANTED

    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
        } else {
            ActivityCompat.requestPermissions(
                this@RemindersFragment.requireActivity(),
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
            ActivityCompat.requestPermissions(
                this@RemindersFragment.requireActivity(),
                arrayOf<String>(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }
    private fun isPermissionGranted2(): Boolean {
        val granted=  ContextCompat.checkSelfPermission(
                    this@RemindersFragment.requireContext(),
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) === PackageManager.PERMISSION_GRANTED
        Log.i("TAG", "enableMyLocation: permission is? $granted")

        return granted

    }

    private fun enableMyLocation2() {
        if (isPermissionGranted2()) {
//            map.setMyLocationEnabled(true)
            Log.i("TAG", "enableMyLocation2: permission is granted")
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(
                    this@RemindersFragment.requireActivity(),
                    arrayOf(
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ),
                    11
                )
            } else {
                ActivityCompat.requestPermissions(
                    this@RemindersFragment.requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
                )
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.size > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    override fun onItemClick(position: Int) {
        //get the reminder that is clicked on
        val reminder = viewModel.list2.get(position)
        val nav = findNavController()


        nav.navigate(RemindersFragmentDirections.actionRemindersFragmentToEditReminderFragment(reminder))

    }


}