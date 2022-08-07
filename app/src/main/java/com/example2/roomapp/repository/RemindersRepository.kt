package com.example2.roomapp.repository

import androidx.lifecycle.LiveData
import com.example2.roomapp.data.RemainderDao
import com.example2.roomapp.data.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import com.example2.roomapp.data.Result
import com.example2.roomapp.data.database.RemindersDatabase
import com.example2.roomapp.data.database.networkBoundResource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import javax.inject.Inject


class RemindersRepository @Inject constructor(private val database: RemindersDatabase) : ReminderRepo {


    val reminderDao: RemainderDao = database.reminderDao()

    override suspend fun getReminders(): Result<List<Reminder>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Result.Success(reminderDao.getReminders())
            } catch (ex: Exception) {
                Result.Error(ex.localizedMessage)
            }
        }


    override suspend fun getReminder(id: String): Result<Reminder> = withContext(Dispatchers.IO) {
        try {
            val reminder = reminderDao.getReminderById(id)
            if (reminder != null) {
                return@withContext Result.Success(reminder)
            } else {
                return@withContext Result.Error("Reminder not found!")
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e.localizedMessage)
        }
    }


    fun getRestaurants() = networkBoundResource(
        query = {

            reminderDao.getAllReminders2()
        },
        fetch = {
            var pr: ArrayList<Reminder> = arrayListOf()
            pr
        },
        saveFetchResult = { astroid ->
        }
    )


    override suspend fun deleteReminder(reminder: Reminder) {
        withContext(Dispatchers.IO) {
            database.reminderDao().delete(reminder)
        }
    }

    override suspend fun insertReminder(reminder: Reminder) {
        withContext(Dispatchers.IO) {
            database.reminderDao().insertReminder(reminder)
        }
    }

    override suspend fun deleteAllReminders() {
        withContext(Dispatchers.IO) {
            reminderDao.deleteAllReminders()
        }
    }
}