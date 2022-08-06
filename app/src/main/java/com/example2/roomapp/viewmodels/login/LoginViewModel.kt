package com.example2.roomapp.viewmodels.login

import android.app.Application
import androidx.lifecycle.*
import com.example2.roomapp.data.Reminder
import com.example2.roomapp.data.database.RemindersDatabase
import com.example2.roomapp.firebase.FirebaseUserLiveData
import com.example2.roomapp.repository.RemindersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch


class LoginViewModel(val database: RemindersDatabase, application: Application): AndroidViewModel(application) {
    private var repository: RemindersRepository = RemindersRepository(database)

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