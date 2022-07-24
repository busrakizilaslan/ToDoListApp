package com.busrayalcin.todolistapp.ui.viewmodel


import androidx.lifecycle.ViewModel
import com.busrayalcin.todolistapp.data.repo.TaskDaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateTaskFragmentViewModel @Inject constructor(var repo : TaskDaoRepo) : ViewModel() {

    fun updateTask(task_id:Int,task_title:String,task_description:String,task_date:String,task_time:String){
        repo.updateTask(task_id,task_title,task_description,task_date,task_time)
    }
}