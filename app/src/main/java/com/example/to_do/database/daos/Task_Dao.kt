package com.example.to_do.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.to_do.database.entity.Task

@Dao
interface Task_Dao {

    @Insert
    fun addTask(task: Task)
    @Delete
    fun deleteTask(task : Task)
    @Update
    fun updateTask(task: Task)

    @Query("SELECT * FROM Task ")
    fun showAll() : List<Task>

    @Query("""
    SELECT * FROM Task
    WHERE date BETWEEN :startOfDay AND :endOfDay
""")
    fun getTasksByDay(startOfDay: Long, endOfDay: Long): List<Task>

    @Query("""
    SELECT * FROM Task
    WHERE id == :id
""")
    fun getTasksById(id :  Int): Task?
}