package com.example.to_do.database

import android.content.Context
import android.os.Build
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.to_do.database.daos.Task_Dao
import com.example.to_do.database.entity.Task

@Database(entities = [Task::class], version = 1)
 open abstract class Data_Base() : RoomDatabase() {
    abstract fun UserDao() : Task_Dao

     companion object{
       private   var dataBase : Data_Base? = null
         fun createDataBase(context: Context){

             dataBase =
                 Room
                     .databaseBuilder(context, Data_Base::class.java,"Task")
                     .build()


         }
         fun getDatabase() : Data_Base {
             return dataBase!!
         }
     }



}