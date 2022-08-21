package com.example2.roomapp.viewmodels.login

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example2.roomapp.data.database.RemindersDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.IllegalArgumentException
import javax.inject.Inject

class LoginViewModelFactory @Inject constructor(
    private val dataSource: RemindersDatabase,
    private val application: Application,
    defaultArgs: Bundle? = null,
    savedStateRegistryOwner: SavedStateRegistryOwner
) : AbstractSavedStateViewModelFactory(savedStateRegistryOwner,defaultArgs){


    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(handle,dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")    }


}