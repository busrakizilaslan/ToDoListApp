package com.busrayalcin.todolistapp.di

import android.content.Context
import androidx.room.Room
import com.busrayalcin.todolistapp.data.repo.TaskDaoRepo
import com.busrayalcin.todolistapp.room.TaskDao
import com.busrayalcin.todolistapp.room.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideTaskDaoRepo(dao: TaskDao) : TaskDaoRepo{
        return TaskDaoRepo(dao)
    }

    @Provides
    @Singleton
    fun provideTaskDao(@ApplicationContext context: Context) : TaskDao{
        val vt = Room.databaseBuilder(context,TaskDatabase::class.java,"tasks.sqlite")
            .createFromAsset("tasks.sqlite")
            .build()
        return vt.getTaskDao()
    }
}