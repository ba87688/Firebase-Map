package com.example2.roomapp.repository

import com.example2.roomapp.data.Reminder
import com.example2.roomapp.data.Result
import com.example2.roomapp.data.database.networkBoundResource

interface ReminderRepo {

    suspend fun getReminders():Result<List<Reminder>>

    suspend fun getReminder(id: String): Result<Reminder>


    suspend fun deleteReminder(reminder: Reminder)

    suspend fun insertReminder(reminder: Reminder)

    suspend fun deleteAllReminders()


    }