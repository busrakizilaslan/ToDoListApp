package com.busrayalcin.todolistapp.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.busrayalcin.todolistapp.R
import com.busrayalcin.todolistapp.data.entity.Task
import com.busrayalcin.todolistapp.databinding.TaskRowBinding
import com.busrayalcin.todolistapp.ui.fragment.MainFragmentDirections
import com.busrayalcin.todolistapp.ui.viewmodel.MainFragmentViewModel
import com.busrayalcin.todolistapp.utils.doNavigate
import com.google.android.material.snackbar.Snackbar

class TaskAdapter(var mContext: Context,
                  var taskList:List<Task>,
                  var viewModel: MainFragmentViewModel)
    : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(binding: TaskRowBinding) : RecyclerView.ViewHolder(binding.root){
        var binding : TaskRowBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding : TaskRowBinding = DataBindingUtil.inflate(layoutInflater,R.layout.task_row,parent,false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        val t = holder.binding
        t.taskObject = task

        t.cardViewTask.setOnClickListener {
            val direction = MainFragmentDirections.actionMainFragmentToUpdateTaskFragment(task)
            Navigation.doNavigate(it,direction)
        }

        t.ivDelete.setOnClickListener {
            Snackbar.make(it,"${task.task_title} will be deleted?", Snackbar.LENGTH_LONG)
                .setAction("YES"){
                    viewModel.delete(task.task_id)
                }.show()
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}