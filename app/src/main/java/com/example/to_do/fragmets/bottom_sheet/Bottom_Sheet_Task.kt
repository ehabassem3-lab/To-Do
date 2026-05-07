package com.example.to_do.fragmets.bottom_sheet

import android.app.DatePickerDialog
import java.util.Calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.to_do.database.Data_Base
import com.example.to_do.database.entity.Task
import com.example.to_do.databinding.CreateTaskItemBinding
import com.example.to_do.day
import com.example.to_do.format
import com.example.to_do.month
import com.example.to_do.year
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Bottom_Sheet_Task(var AddTask : () -> Unit) : BottomSheetDialogFragment() {
    var selectedDate = Calendar.getInstance()
    lateinit var binding: CreateTaskItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         binding = CreateTaskItemBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        binding.TaskDate.text = selectedDate.format()
    }


    fun initListeners(){
        binding.CraeteTaskButton.setOnClickListener {
            if (!validate())  return@setOnClickListener
            val task = Task(
                title = binding.AddTaskName.editText!!.text.toString(),
                descreption =  binding.AddTaskDetails.editText!!.text.toString(),
                date =  selectedDate.timeInMillis,
                )
            Data_Base.getDatabase().UserDao().addTask(task)
            AddTask()
            dismiss()




        }

        binding.TaskDate.setOnClickListener {
           var picker = DatePickerDialog(requireActivity(),
               {
                   view, year, month, dayOfMonth -> binding.TaskDate.text = "${dayOfMonth}/${month+1}/${year}"
                   selectedDate.set(Calendar.YEAR,year)
                   selectedDate.set(Calendar.MONTH,month)
                   selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
               },
               selectedDate.year(),
               selectedDate.month(),
               selectedDate.day())
            picker.show()
        }

    }
    fun validate() : Boolean{
        var isValid = true
        if (binding.AddTaskName.editText!!.text.isEmpty()){
            binding.AddTaskName.error = "Task Name Is Required"
            isValid = false
        } else{
            binding.AddTaskName.error =null


        }
        if (binding.AddTaskDetails.editText!!.text.isEmpty()){
            binding.AddTaskDetails.error = "Task Details Is Required"
            isValid = false
        } else{
            binding.AddTaskDetails.error = null
        }

        return isValid
    }



}