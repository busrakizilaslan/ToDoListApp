package com.busrayalcin.todolistapp.room

import androidx.room.*
import com.busrayalcin.todolistapp.data.entity.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    suspend fun allTasks() : List<Task>

    @Insert
    suspend fun addTask(task: Task) : Long

    @Update
    suspend fun updateTask(task: Task) : Int

    @Delete
    suspend fun deleteTask(task: Task) : Int

    @Query("SELECT * FROM tasks WHERE task_title like '%' || :searchWord || '%'")
    suspend fun searchTask(searchWord:String) : List<Task>
}