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



class RemindersRepository(private val database: RemindersDatabase) {
    val reminderDao: RemainderDao = database.reminderDao()
//    val astroids: LiveData<List<Reminder>> = reminderDao.getAllReminders()
    val astroids: Flow<List<Reminder>> = reminderDao.getAllReminders3()

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




    fun getRestaurants() = networkBoundResource(
        query = {

            reminderDao.getAllReminders2()
        },
        fetch = {
            var pr : ArrayList<Reminder> = arrayListOf()
//            withContext(Dispatchers.IO) {
//                val formattedDateList = getRealParsedResponse()
//
//
//                val data = service.getAstroids3(formattedDateList.first(),formattedDateList.last())
//                val you = Gson().toJson(data)
//                val you1 = JSONObject(you)
//                val pr1 = parseAsteroidsJsonResult(you1)
//                withContext(Dispatchers.IO){
//                    pr= pr1
//                }
//            }
            pr
        },
        saveFetchResult = { astroid->

//            reminderDao.withTransaction {
//                database.assDatabaseDao.deleteAllAstroids()
//                database.assDatabaseDao.insertList(astroid)
//            }
        }
    )

    fun getReminders3() = networkBoundResource(
        query = {

            reminderDao.getAllReminders3()
        },
        fetch = {
            var pr : ArrayList<Reminder> = arrayListOf()
//            withContext(Dispatchers.IO) {
//                val formattedDateList = getRealParsedResponse()
//
//
//                val data = service.getAstroids3(formattedDateList.first(),formattedDateList.last())
//                val you = Gson().toJson(data)
//                val you1 = JSONObject(you)
//                val pr1 = parseAsteroidsJsonResult(you1)
//                withContext(Dispatchers.IO){
//                    pr= pr1
//                }
//            }
            pr
        },
        saveFetchResult = { astroid->

//            reminderDao.withTransaction {
//                database.assDatabaseDao.deleteAllAstroids()
//                database.assDatabaseDao.insertList(astroid)
//            }
        }
    )


    suspend fun deleteAllReminders() {
        withContext(Dispatchers.IO) {
            reminderDao.deleteAllReminders()
        }
    }
}