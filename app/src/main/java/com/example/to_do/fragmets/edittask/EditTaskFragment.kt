package com.example.to_do.fragmets.edittask


import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.to_do.R
import com.example.to_do.database.Data_Base
import com.example.to_do.database.entity.Task
import com.example.to_do.databinding.ActivityMainBinding
import com.example.to_do.databinding.EditTaskBinding
import com.example.to_do.databinding.SettingsBinding
import com.example.to_do.day
import com.example.to_do.fragmets.task.Task_Fragment
import com.example.to_do.month
import com.example.to_do.year
import java.util.Calendar

class EditTask : AppCompatActivity() {
    var selectedDate = Calendar.getInstance()
    lateinit var task: Task
    lateinit var binding: EditTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = EditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        task = intent.getSerializableExtra("task") as Task
        binding.AddTaskName.editText!!.setText(task.title)
        binding.AddTaskDetails.editText!!.setText(task.descreption)
        Log.e("Task" ,"${task}")
        initListeners()


        binding.BackarrowEditTask.setOnClickListener {
            finish()


        }
    }
    fun editTask(){

           task.title = binding.AddTaskName.editText!!.text.toString() ?: ""
        task.descreption = binding.AddTaskDetails.editText!!.text.toString() ?: ""
          task.date = selectedDate.timeInMillis
            task.isDone = intent.getBooleanExtra("isDone", false)

        Data_Base.getDatabase().UserDao().updateTask(task)
    }
    fun initListeners(){
        binding.CraeteTaskButton.setOnClickListener {
            if (!validate())  return@setOnClickListener
            editTask()
            finish()




        }

        binding.TaskDate.setOnClickListener {
            var picker = DatePickerDialog(this@EditTask,
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