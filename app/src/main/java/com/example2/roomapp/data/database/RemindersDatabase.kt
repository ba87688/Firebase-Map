package com.example2.roomapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example2.roomapp.data.RemainderDao
import com.example2.roomapp.data.Reminder

@Database(entities = [Reminder::class], version = 1, exportSchema = false)
abstract class RemindersDatabase : RoomDatabase() {
    abstract fun reminderDao(): RemainderDao


    companion object {
        @Volatile
        private var INSTANCE: RemindersDatabase? = null

        fun getDatabase(context: Context): RemindersDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RemindersDatabase::class.java,
                    "reminders_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }


    }
}