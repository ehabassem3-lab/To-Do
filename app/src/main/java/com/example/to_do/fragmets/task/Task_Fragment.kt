package com.example.to_do.fragmets.task

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do.DayViewContainer
import com.example.to_do.R
import com.example.to_do.database.Data_Base
import com.example.to_do.database.entity.Task
import com.example.to_do.databinding.FragmentTaskBinding
import com.example.to_do.fragmets.bottom_sheet.Bottom_Sheet_Task
import com.example.to_do.generateCurrentMonthDates
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.WeekDayBinder
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.time.Duration.Companion.days
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.to_do.fragmets.edittask.EditTask

class Task_Fragment : Fragment() {
    var tasks= emptyList<Task>()
    val calendar = Calendar.getInstance()
    val endOfDay = null
    val startDay = null
    lateinit var intent : Intent
    var adapterTasks = TaskAdabter(emptyList() ,
        EditTask = { position ->

             intent = Intent(requireContext(), EditTask::class.java)
            intent.putExtra("task", tasks[position])
            startActivity(intent)
            RefreshTask()
        }
    ) { position ->
        if (selectedDate== null){
            val task = tasks[position]
            task.isDone = !task.isDone
            Data_Base.getDatabase().UserDao().updateTask(task)
            RefreshTask()
        }
          else{
            val task = tasks[position]
            task.isDone = !task.isDone
            Data_Base.getDatabase().UserDao().updateTask(task)
            val millis = localDateToMillis(selectedDate!!)
            RefreshTaskByDate(millis)


        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    val dates = generateCurrentMonthDates()
     var selectedDate : LocalDate? = null


    lateinit var recyclerViewTasks: RecyclerView



    lateinit var  binding: FragmentTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RefreshTask()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTaskBinding.inflate(layoutInflater,container,false)
        RefreshTask()
        return  binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCalender()
        recyclerViewTasks = binding.TasksRecycler
        recyclerViewTasks.adapter = adapterTasks
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = tasks[position]
                Data_Base.getDatabase().UserDao().deleteTask(task =  tasks[position])
                RefreshTask()
            }

            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                return 1.0f
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView

                val maxSwipe = 300f
                val clampedDX = maxOf(dX, -maxSwipe)


                val paint = Paint().apply { color = Color.RED }

                c.drawRect(
                    itemView.right + clampedDX,
                    itemView.top.toFloat(),
                    itemView.right.toFloat(),
                    itemView.bottom.toFloat(),
                    paint
                )

                // 🗑️ delete icon
                val icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delte)

                icon?.let {
                    val margin = (itemView.height - it.intrinsicHeight) / 2

                    val top = itemView.top + margin
                    val bottom = top + it.intrinsicHeight

                    val left = itemView.right - margin - it.intrinsicWidth
                    val right = itemView.right - margin

                    it.setBounds(left, top, right, bottom)
                    it.draw(c)
                }

                super.onChildDraw(c, recyclerView, viewHolder, clampedDX, dY, actionState, isCurrentlyActive)
            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerViewTasks)

        itemTouchHelper.attachToRecyclerView(recyclerViewTasks)
    }
    override fun onResume() {
        super.onResume()
        RefreshTask()
    }

    fun RefreshTask(){
         tasks =  Data_Base.getDatabase().UserDao().showAll()
        adapterTasks.tasks = tasks
        adapterTasks.notifyDataSetChanged()


    }
    @SuppressLint("NotifyDataSetChanged")
    fun RefreshTaskByDate(dateMillis: Long,) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateMillis
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfDay = calendar.timeInMillis

        tasks = Data_Base.getDatabase().UserDao().getTasksByDay(startOfDay, endOfDay)
        adapterTasks.tasks = tasks
        adapterTasks.notifyDataSetChanged()


    }
    fun initCalender(){
        binding.datesRecycle.dayBinder = object : WeekDayBinder<DayViewContainer>{
            override fun create(view: View): DayViewContainer {
              return DayViewContainer(view)
            }

            @SuppressLint("ResourceAsColor")
            override fun bind(
                container: DayViewContainer,
                data: WeekDay
            ) {

                container.DayName.text = data.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                container.DayDate.text = "${data.date.dayOfMonth}"

                if(selectedDate == data.date){
                    TextViewCompat.setTextAppearance(container.DayDate, R.style.selectedDate)
                    TextViewCompat.setTextAppearance(container.DayName, R.style.selectedDate)

                }
                else{
                    TextViewCompat.setTextAppearance(container.DayDate, R.style.unSelectedDate)
                    TextViewCompat.setTextAppearance(container.DayName, R.style.unSelectedDate)
                }

                container.view.setOnClickListener {
                    val currentDate = selectedDate
                    if (currentDate == data.date){
                        selectedDate = null
                        RefreshTask()
                        binding.datesRecycle.notifyWeekChanged(currentDate)
                    }
                    else{
                        selectedDate  = data.date
                        val selectedMillis = localDateToMillis(data.date)
                        RefreshTaskByDate(selectedMillis)
                        binding.datesRecycle.notifyWeekChanged(data.date)
                    }
                }


            }

        }
        val currentDate = LocalDate.now()
        val currentMonth = YearMonth.now()
        val startDate = currentMonth.minusMonths(1).atStartOfMonth() // Adjust as needed
        val endDate = currentMonth.plusMonths(1).atEndOfMonth() // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
       binding.datesRecycle.setup(startDate, endDate, firstDayOfWeek)
        binding.datesRecycle.scrollToWeek(currentDate)


    }
    private fun localDateToMillis(date: LocalDate): Long {
        return date.atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }



}