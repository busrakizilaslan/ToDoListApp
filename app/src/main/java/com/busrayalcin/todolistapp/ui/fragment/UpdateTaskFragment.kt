package com.busrayalcin.todolistapp.ui.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.busrayalcin.todolistapp.R
import com.busrayalcin.todolistapp.databinding.FragmentUpdateTaskBinding
import com.busrayalcin.todolistapp.ui.viewmodel.UpdateTaskFragmentViewModel
import com.busrayalcin.todolistapp.utils.doNavigate
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class UpdateTaskFragment : Fragment() {
    private lateinit var binding : FragmentUpdateTaskBinding
    private lateinit var viewModel : UpdateTaskFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : UpdateTaskFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_update_task,container,false)
        binding.updateTaskFragment = this
        binding.updateTaskToolbarTitle = "Update Task"

        backPress()
        setDate()
        setTime()

        val bundle : UpdateTaskFragmentArgs by navArgs()
        val takenTask = bundle.task
        binding.taskObject = takenTask

        return binding.root
    }

    fun updateTaskButton(task_id:Int,task_title:String,task_description:String,task_date:String,task_time:String){
        if (task_title.isEmpty() || task_description.isEmpty() || task_date.isEmpty() || task_time.isEmpty()) {
            Snackbar.make(binding.buttonUpdate,"Please fill all empty fields.", Snackbar.LENGTH_LONG).show()
        }
        else{
            viewModel.updateTask(task_id,task_title,task_description,task_date,task_time)
            val direction = UpdateTaskFragmentDirections.actionUpdateTaskFragmentToMainFragment()
            Navigation.doNavigate(binding.buttonUpdate, direction)
            Snackbar.make(binding.buttonUpdate,"Task $task_title updated.", Snackbar.LENGTH_LONG).show()
        }
    }

    fun backPress(){
        binding.toolbarUpdateTask.setNavigationIcon(R.drawable.back_24)
        binding.toolbarUpdateTask.setNavigationOnClickListener {
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