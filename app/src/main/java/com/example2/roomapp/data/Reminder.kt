package com.example2.roomapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "reminders")
data class Reminder(
    @ColumnInfo(name = "title") var title:String?,
    @ColumnInfo(name = "description") var description:String?,
    @ColumnInfo(name = "location") var location:String?,
    @ColumnInfo(name = "latitude") var latitude:String?,
    @ColumnInfo(name = "longitude") var longitude:String?,
    @PrimaryKey @ColumnInfo(name = "entry_id") val id:String = UUID.randomUUID().toString()
) {
}