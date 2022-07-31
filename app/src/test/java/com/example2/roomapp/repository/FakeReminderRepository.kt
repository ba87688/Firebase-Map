package com.example2.roomapp.repository

import androidx.lifecycle.MutableLiveData
import com.example2.roomapp.data.Reminder
import com.example2.roomapp.data.Result
import java.lang.Exception

class FakeReminderRepository :ReminderRepo{

    private val remindersItems = mutableListOf<Reminder>()

    private val observableReminderItems = MutableLiveData<List<Reminder>>(remindersItems)


    override suspend fun getReminders(): Result<List<Reminder>> {

        return try {
            Result.Success(remindersItems)
        } catch (ex: Exception){
            Result.Error(ex.localizedMessage)
        }

    }

    override suspend fun getReminder(id: String): Result<Reminder> {
        var rem:Reminder? =null

        for (reminder in remindersItems){
            if (reminder.id==id){
                rem = reminder
            }
        }
        if (rem!=null) {
            return Result.Success(rem)
        }
        else{
            return Result.Error("Reminder not found!")
        }


    }

    override suspend fun deleteReminder(reminder: Reminder) {
        remindersItems.remove(reminder)
        refreshLiveData()
    }

    private fun refreshLiveData(){
        observableReminderItems.postValue(remindersItems)
    }

    override suspend fun insertReminder(reminder: Reminder) {
        remindersItems.add(reminder)
        refreshLiveData()
    }

    override suspend fun deleteAllReminders() {
        remindersItems.clear()
        refreshLiveData()
    }

}