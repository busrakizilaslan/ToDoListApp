package com.busrayalcin.todolistapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.busrayalcin.todolistapp.data.entity.Task
import com.busrayalcin.todolistapp.data.repo.TaskDaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskFragmentViewModel @Inject constructor(var repo : TaskDaoRepo) : ViewModel() {

    fun newTask(task_title:String,task_description:String,task_date:String,task_time:String){
        repo.addTask(task_title,task_description,task_date,task_time)
    }
}