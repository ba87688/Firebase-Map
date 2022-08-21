package com.example2.roomapp.viewmodels.login

import android.app.Application
import androidx.lifecycle.*
import androidx.room.RoomDatabase
import com.example2.roomapp.data.Reminder
import com.example2.roomapp.data.database.RemindersDatabase
import com.example2.roomapp.firebase.FirebaseUserLiveData
import com.example2.roomapp.repository.RemindersRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @AssistedInject constructor(
    @Assisted val state: SavedStateHandle,
    val database: RemindersDatabase,
    application: Application): AndroidViewModel(application) {

    var list2: List<Reminder>
    init {
        list2 =  mutableListOf()
    }

    var repository: RemindersRepository = RemindersRepository( database)


    val restaurants = repository.getRestaurants().asLiveData()

    enum class AuthenticationState{
        AUTHENTICATED, UNAUTHENTICATED
    }

    val authenticationState = FirebaseUserLiveData().map { user->
        if(user!=null){
            AuthenticationState.AUTHENTICATED
        }else{
            AuthenticationState.UNAUTHENTICATED
        }
    }

    fun deleteReminder(reminder: Reminder){
        viewModelScope.launch {
            repository.deleteReminder(reminder)
        }
    }
    fun insertReminder(reminder: Reminder){
        viewModelScope.launch {
            repository.insertReminder(reminder)
        }
    }
}