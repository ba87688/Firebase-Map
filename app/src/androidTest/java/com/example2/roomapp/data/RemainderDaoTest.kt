package com.example2.roomapp.data



import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example2.roomapp.data.database.RemindersDatabase
import com.example2.roomapp.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RemainderDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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
    fun insertReminder3() =  runBlockingTest {
//        val reminder = Reminder("Evan","Description","4444","4433","4343","2")
//        dao.insertReminder(reminder)
//
//        val allReminderItems=  dao.getAllReminders().getOrAwaitValue()

            Log.i("TAG", "insertReminder3: Testing")
        val name ="evan"
        assertThat(name).isEqualTo("evan")



    }



    //insert reminder test
    @Test
    fun insertReminder() =  runBlockingTest {
        val reminder = Reminder("Evan","Description","4444","4433","4343","2")
        dao.insertReminder(reminder)

        val allReminderItems=  dao.getAllReminders().getOrAwaitValue()

        assertThat(allReminderItems).contains(reminder)



    }






}