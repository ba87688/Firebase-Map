package com.example2.roomapp.viewmodels.login

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example2.roomapp.data.RemainderDao
import com.example2.roomapp.data.database.RemindersDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginViewModelTest {
    private lateinit var database: RemindersDatabase
    private lateinit var dao: RemainderDao


    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.reminderDao()

    }
    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

}