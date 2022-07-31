package com.example2.roomapp.viewmodels.login

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example2.roomapp.data.database.RemindersDatabase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginViewModelTest{

    private lateinit var viewModel:LoginViewModel

    @Before
    fun setup(){
        val data = RemindersDatabase.getDatabase(ApplicationProvider.getApplicationContext())
        viewModel = LoginViewModel(data,ApplicationProvider.getApplicationContext())
    }

    @Test
    fun testTesting(){
        viewModel.database.reminderDao()
    }
}