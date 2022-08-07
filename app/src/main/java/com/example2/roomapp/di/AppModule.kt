package com.example2.roomapp.di

import android.content.Context
import androidx.room.Room
import com.example2.roomapp.data.database.RemindersDatabase
import com.example2.roomapp.other.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideReminderDatabase(
        @ApplicationContext context:Context
    )=Room.databaseBuilder(context,RemindersDatabase::class.java, DATABASE_NAME).build()


    @Provides
    @Singleton
    fun provideReminderDatabaseDao(db:RemindersDatabase) = db.reminderDao()
}