package com.example2.roomapp.viewmodels.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example2.roomapp.data.database.RemindersDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.IllegalArgumentException
import javax.inject.Inject

class LoginViewModelFactory @Inject constructor(
    private val dataSource: RemindersDatabase,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }


}