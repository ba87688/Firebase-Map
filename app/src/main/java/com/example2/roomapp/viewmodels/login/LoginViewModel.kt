package com.example2.roomapp.viewmodels.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.example2.roomapp.data.database.RemindersDatabase
import com.example2.roomapp.firebase.FirebaseUserLiveData
import com.example2.roomapp.repository.RemindersRepository


class LoginViewModel(val database: RemindersDatabase, application: Application): AndroidViewModel(application) {
    private var repository: RemindersRepository = RemindersRepository(database)

//    val restaurants = repository.reminderDao.getAllReminders2().asLiveData()

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