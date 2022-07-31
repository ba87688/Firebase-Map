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
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
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


    //insert reminder test
    @Test
    fun insertReminder() =  runBlockingTest {
        val reminder = Reminder("Evan","Description","4444","4433","4343","2")
        dao.insertReminder(reminder)

        val allReminderItems=  dao.getAllReminders().getOrAwaitValue()

        assertThat(allReminderItems).contains(reminder)

    }
    @Test
    fun deleteReminder() = runBlockingTest{
        val reminder = Reminder("Evan","Description","4444","4433","4343","2")
        dao.insertReminder(reminder)
        dao.delete(reminder)

        val allReminderItems=  dao.getAllReminders().getOrAwaitValue()

        assertThat(allReminderItems).doesNotContain(reminder)

    }

    @Test
    fun deleteAllReminders() = runBlockingTest{
        val reminder1 = Reminder("Evan1","Descriptin","444","443","4343","4")
        val reminder2 = Reminder("Evan2","Descriptio","4444","4433","4343","5")
        val reminder3 = Reminder("Evan3","Descripion","4444","4433","4343","6")
        dao.insertReminder(reminder1)
        dao.insertReminder(reminder2)
        dao.insertReminder(reminder3)

        dao.deleteAllReminders()
        val allReminderItems=  dao.getAllReminders().getOrAwaitValue()

        assertThat(allReminderItems).isEmpty()

    }

    @Test
    fun gettingReminderById() = runBlockingTest {
        val reminder1 = Reminder("Evan1","Descriptin","444","443","4343","4")
        val reminder2 = Reminder("Evan2","Descriptio","4444","4433","4343","5")
        val reminder3 = Reminder("Evan3","Descripion","4444","4433","4343","6")
        dao.insertReminder(reminder1)
        dao.insertReminder(reminder2)
        dao.insertReminder(reminder3)

        val reminderToTest = dao.getReminderById("4")

        assertThat(reminderToTest).isEqualTo(reminder1)
    }

    @Test
    fun gettingReminderlist() = runBlockingTest {
        val reminder1 = Reminder("Evan1","Descriptin","444","443","4343","4")
        val reminder2 = Reminder("Evan2","Descriptio","4444","4433","4343","5")
        val reminder3 = Reminder("Evan3","Descripion","4444","4433","4343","6")
        dao.insertReminder(reminder1)
        dao.insertReminder(reminder2)
        dao.insertReminder(reminder3)

        val list = dao.getReminders()
        assertThat(list).containsExactly(reminder1,reminder2,reminder3)
    }

    @InternalCoroutinesApi
    @Test
    fun getAllRemindersFlow() = runBlockingTest {
        val reminder = Reminder("Evan","Description","4444","4433","4343","2")
        dao.insertReminder(reminder)
        val actual = mutableListOf<Reminder>()
        val allReminderItems=  dao.getAllReminders2().first()
        val itemInList = allReminderItems.get(0)



        assertThat(itemInList).isEqualTo(reminder)

    }




}