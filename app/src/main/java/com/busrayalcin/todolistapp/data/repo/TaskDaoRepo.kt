package com.busrayalcin.todolistapp.data.repo

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.busrayalcin.todolistapp.data.entity.Task
import com.busrayalcin.todolistapp.room.TaskDao
import com.busrayalcin.todolistapp.ui.fragment.MainFragmentDirections
import com.busrayalcin.todolistapp.ui.fragment.UpdateTaskFragmentDirections
import com.busrayalcin.todolistapp.utils.doNavigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskDaoRepo (var dao : TaskDao){

    var taskList : MutableLiveData<List<Task>>
    init {
        taskList = MutableLiveData()
    }

    fun takeTasks(): MutableLiveData<List<Task>> {
        return taskList
    }

    fun takeAllTasks() {
        val job = CoroutineScope(Dispatchers.Main).launch {
            taskList.value = dao.allTasks()
        }
    }

    fun searchTask(searchWord:String) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            taskList.value = dao.searchTask(searchWord)
        }
    }


    fun addTask(task_title:String,task_description:String,task_date:String,task_time:String){
        val job = CoroutineScope(Dispatchers.Main).launch {
            val newTask = Task(0,task_title,task_description,task_date,task_time)
            val addReturn = dao.addTask(newTask)
            println("Added: $addReturn")
        }
        Log.e("New Task", task_title)
    }

    fun deleteTask(task_id:Int){
        val job = CoroutineScope(Dispatchers.Main).launch {
            val deletedTask = Task(task_id,"","","","")
            val deleteReturn = dao.deleteTask(deletedTask)
            takeAllTasks()
            println("Deleted: $deleteReturn")
        }
    }

    fun updateTask(task_id:Int,task_title:String,task_description:String,task_date:String,task_time:String){
        val job = CoroutineScope(Dispatchers.Main).launch {
            val updatedTask = Task(task_id,task_title,task_description,task_date,task_time)
            val updateReturn = dao.updateTask(updatedTask)
            println("Updated: $updateReturn")
        }
    }


}