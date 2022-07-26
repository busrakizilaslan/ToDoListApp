package com.busrayalcin.todolistapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.busrayalcin.todolistapp.data.entity.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getTaskDao() : TaskDao
}