package com.example2.roomapp.viewmodels.reminderlocation

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {
    var number:String? =null

    init {
        number = "EVan"
        viewModelScope.launch {

        }
    }
}