package com.example2.roomapp.fragments.reminderlist

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
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
import com.example2.roomapp.viewmodels.login.LoginViewModel
import com.example2.roomapp.viewmodels.login.LoginViewModelFactory
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.launch

class RemindersFragment : Fragment() {
    private var _binding: FragmentRemindersBinding? = null
    private val binding get() = _binding!!
//    private val viewModel : LoginViewModel by viewModels()
    private lateinit var viewModel:LoginViewModel
    private val REQUEST_LOCATION_PERMISSION = 1
    var i = 0


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


        val application = requireNotNull(this.activity).application
        val dataSource = RemindersDatabase.getDatabase(application)
        val viewModelFactory = LoginViewModelFactory(dataSource,application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)



        enableMyLocation()


        val db = Room.databaseBuilder(activity?.applicationContext!!,RemindersDatabase::class.java,"reminders_database").allowMainThreadQueries().build()

        lifecycleScope.launch {
//            db.reminderDao().insertReminder(Reminder("Hi","desceibe","er","33","rr","t"))
//            db.reminderDao().insertReminder(Reminder("Tarzan","red","god","3","rr","tiger"))
//            val r = db.reminderDao().getReminderById("t")
//            Log.i("TAG", "onCreateView: $r")

            db.reminderDao().deleteAllReminders()
        }







        binding.floatingActionButton.setOnClickListener {
            Log.i("TAG", "onCreateView: clicking on the floating action buttom")
            val controller = findNavController()
            controller.navigate(RemindersFragmentDirections.actionRemindersFragmentToCurrentlocationfragment())
            Log.i("TAG", "onCreateView: clicking on the floating action LEAVING")

        }

        viewModel.restaurants.observe(viewLifecycleOwner, Observer {it->
            val list = it.data
            val size = list?.size
            Log.i("TAG", "LIST SIZE : $size ")
            if(list !=null){
                binding.reminderRecyclerView.adapter = RemainderRecyclerViewAdapter(list)
            }
            if(list?.size==0 ||list==null) {
                Log.i("TAG", "went in : $size ")

                if (i<1) {
                    binding.reminderRecyclerView.visibility = View.GONE
                    binding.frameLayout.visibility = View.GONE
                    val imageView = ImageView(this@RemindersFragment.context)
                    imageView.setImageResource(R.drawable.ic_no_data)
                    binding.reminderLinearView.gravity = Gravity.CENTER
                    binding.reminderLinearView.addView(imageView)
                    Log.i("TAG", "onCreateView: this list is empty ")
                    i = i + 1
                }

            }

//            else{
//
//                binding.reminderRecyclerView.visibility= View.GONE
//                val imageView:ImageView = ImageView(this@RemindersFragment.context)
//                imageView.setImageResource(R.drawable.ic_no_data)
//                binding.reminderLinearView.gravity = Gravity.CENTER
//                binding.reminderLinearView.addView(imageView)
//                Log.i("TAG", "onCreateView: this list is empty ")
//
//
//            }



//            Log.i("TAG", "onCreateView: creating things ${obser.id} ")
        })


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







    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            this@RemindersFragment.requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED
    }
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
//            map.setMyLocationEnabled(true)
            Log.i("TAG", "enableMyLocation: permission is granted")
        }
        else {
            ActivityCompat.requestPermissions(
                this@RemindersFragment.requireActivity(),
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.size > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }
}