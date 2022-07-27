package com.example2.roomapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface RemainderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder)

    @Delete
    suspend fun delete(reminder: Reminder)

    @Query("DELETE FROM reminders")
    suspend fun deleteAllReminders()

    @Query("SELECT * FROM reminders where entry_id=:reminderId")
    suspend fun getReminderById(reminderId: String): Reminder?



    @Query("SELECT * FROM reminders")
    suspend fun getReminders(): List<Reminder>

//    @Query("SELECT * FROM reminders")
//    suspend fun getLiveReminders(): LiveData<List<Reminder>>

    @Query("SELECT * FROM reminders ORDER BY entry_id DESC")
    fun getAllReminders(): LiveData<List<Reminder>>


    @Query("SELECT * FROM reminders")
    fun getAllReminders2(): Flow<List<Reminder>>

    @Query("SELECT * FROM reminders")
    fun getAllReminders3(): Flow<List<Reminder>>
}