package com.example.to_do.fragmets.task

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do.R
import com.example.to_do.database.entity.Task
import com.example.to_do.databinding.CreateTaskItemBinding
import com.example.to_do.databinding.TaskItemBinding
import java.text.SimpleDateFormat
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

class TaskAdabter(
    var tasks: List<Task> ,
    var EditTask : (position : Int)-> Unit ,
    var TaskClick : (position : Int)-> Unit  ,


):  RecyclerView.Adapter<TaskAdabter.TasksViewHolder>()  {
    lateinit var binding : TaskItemBinding
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TasksViewHolder {
         binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TasksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {

        val task = tasks[position]
        holder.binding.TaskItem.setOnClickListener {
             
        }
        holder.binding.TaskName.text = task.title
        holder.binding.TaskTime.text = sdf.format(Date(task.date))

        holder.binding.TaskStatus.setOnClickListener {
            TaskClick(position)
        }

        if (task.isDone) {
            holder.binding.TaskStatus.background = null
            holder.binding.StatusText.text = "Done !"
            holder.binding.StatusText.textSize = 20f
            holder.binding.StatusText.setTypeface(null, Typeface.BOLD)
            holder.binding.StatusText.setTextColor(Color.GREEN)
            holder.binding.TaskName.setTextColor(Color.GREEN)
            holder.binding.leftColumn.setCardBackgroundColor(Color.GREEN)

        } else {
            val lightBlue = androidx.core.content.ContextCompat.getColor(holder.itemView.context, R.color.Light_Blue)
            holder.binding.TaskStatus.setBackgroundResource(R.drawable.button_round)
            holder.binding.StatusText.setBackgroundResource(R.drawable.ic_true_sign)
            holder.binding.StatusText.text = ""
            holder.binding.TaskName.setTextColor(lightBlue)
            holder.binding.leftColumn.setCardBackgroundColor(lightBlue)
            holder.binding.EditTaskClickContainer.setOnClickListener {
                EditTask(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class TasksViewHolder(val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root){



    }
}