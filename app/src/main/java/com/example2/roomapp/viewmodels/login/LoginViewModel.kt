package com.example2.roomapp.viewmodels.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example2.roomapp.firebase.FirebaseUserLiveData

class LoginViewModel : ViewModel() {
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