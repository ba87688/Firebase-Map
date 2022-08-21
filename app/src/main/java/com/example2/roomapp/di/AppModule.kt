package com.example2.roomapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example2.roomapp.data.database.RemindersDatabase
import com.example2.roomapp.other.Constants.DATABASE_NAME
import com.example2.roomapp.repository.RemindersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideReminderDatabase(
         context: Application
    ):RemindersDatabase= RemindersDatabase.getDatabase(context)


    @Provides
    @Singleton
    fun provideReminderDatabaseDao(db:RemindersDatabase) = db.reminderDao()

  }