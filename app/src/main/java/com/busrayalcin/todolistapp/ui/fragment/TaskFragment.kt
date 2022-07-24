package com.busrayalcin.todolistapp.ui.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.room.ColumnInfo
import com.busrayalcin.todolistapp.R
import com.busrayalcin.todolistapp.databinding.FragmentTaskBinding
import com.busrayalcin.todolistapp.ui.viewmodel.TaskFragmentViewModel
import com.busrayalcin.todolistapp.utils.doNavigate
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TaskFragment : Fragment() {
    private lateinit var binding : FragmentTaskBinding
    private lateinit var viewModel : TaskFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel:TaskFragmentViewModel by viewModels()
        viewModel = tempViewModel
        (activity as AppCompatActivity).supportActionBar?.setIcon(R.drawable.access_time_24)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_task,container,false)
        binding.taskFragment = this
        binding.newTaskToolbarTitle = "New Task"

        backPress()
        setDate()
        setTime()

        return binding.root
    }

    fun saveTask(task_title:String, task_description:String, task_date:String, task_time:String){
        if (task_title.isEmpty() || task_description.isEmpty() || task_date.isEmpty() || task_time.isEmpty()) {
            Snackbar.make(binding.buttonSave,"Please fill all empty fields.", Snackbar.LENGTH_LONG).show()
        }
        else{
            viewModel.newTask(task_title,task_description,task_date,task_time)
            val direction = TaskFragmentDirections.actionTaskFragmentToMainFragment()
            Navigation.doNavigate(binding.buttonSave, direction)
            Snackbar.make(binding.buttonSave,"Task $task_title added.", Snackbar.LENGTH_LONG).show()
        }
    }

    fun backPress(){
        binding.toolbarNewTask.setNavigationIcon(R.drawable.back_24)
        binding.toolbarNewTask.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    fun setDate(){
        binding.setDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)

            val dp = DatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    binding.setDate.setText("$dayOfMonth/${month+1}/$year")
                },year,month,day)

            dp.setButton(DialogInterface.BUTTON_POSITIVE,"Set",dp)
            dp.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",dp)
            dp.show()
        }
    }

    fun setTime(){
        binding.setTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val tp = TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { t, hourOfDay, minute ->
                binding.setTime.setText(String.format("%02d:%02d", hourOfDay,minute))
            },hour,minute,true)

            tp.setButton(DialogInterface.BUTTON_POSITIVE,"Set",tp)
            tp.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",tp)
            tp.show()
        }
    }
}

