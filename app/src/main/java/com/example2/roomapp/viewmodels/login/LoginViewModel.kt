package com.example2.roomapp.viewmodels.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.example2.roomapp.data.Reminder
import com.example2.roomapp.data.database.RemindersDatabase
import com.example2.roomapp.firebase.FirebaseUserLiveData
import com.example2.roomapp.repository.RemindersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toCollection


class LoginViewModel(val database: RemindersDatabase, application: Application): AndroidViewModel(application) {
    private var repository: RemindersRepository = RemindersRepository(database)

//    val restaurants = repository.astroids().asLiveData()
//    val restaurants = repository.reminderDao.getAllReminders2().asLiveData()
    val restaurants = repository.getRestaurants().asLiveData()
//    val astroids: Flow<List<Reminder>> = repository.getReminders3()


    companion object{
        val androidFacts = arrayOf(
            "loser",
            "losing",
            "winning"
        )
    }
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
}