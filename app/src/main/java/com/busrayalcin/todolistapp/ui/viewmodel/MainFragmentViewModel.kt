package com.busrayalcin.todolistapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busrayalcin.todolistapp.data.entity.Task
import com.busrayalcin.todolistapp.data.repo.TaskDaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(var repo : TaskDaoRepo) : ViewModel() {

    var taskList = MutableLiveData<List<Task>>()

    init {
        loadTasks()
        taskList = repo.takeTasks()
    }

    fun loadTasks(){
        repo.takeAllTasks()
    }

    fun search(searchWord:String){
        repo.searchTask(searchWord)
    }
    fun delete(task_id:Int){
        repo.deleteTask(task_id)

    }

}