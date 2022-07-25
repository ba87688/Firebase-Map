package com.example2.roomapp.repository

import com.example2.roomapp.data.RemainderDao
import com.example2.roomapp.data.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import com.example2.roomapp.data.Result

class RemindersRepository(private val reminderDao: RemainderDao) {


    suspend fun getReminders():Result<List<Reminder>> =
        withContext(Dispatchers.IO){
            return@withContext try {
                Result.Success(reminderDao.getReminders())
            }catch (ex:Exception){
                Result.Error(ex.localizedMessage)
            }
        }


    suspend fun insertReminder(reminder:Reminder){
        withContext(Dispatchers.IO){
            reminderDao.insertReminder(reminder)
        }
    }


    suspend fun getReminder(id: String): Result<Reminder> = withContext(Dispatchers.IO) {
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




    suspend fun deleteAllReminders() {
        withContext(Dispatchers.IO) {
            reminderDao.deleteAllReminders()
        }
    }
}