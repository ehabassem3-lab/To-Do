package com.example.to_do

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.to_do.database.Data_Base
import com.example.to_do.database.entity.Task
import com.example.to_do.databinding.ActivityMainBinding
import com.example.to_do.fragmets.bottom_sheet.Bottom_Sheet_Task
import com.example.to_do.fragmets.settings.Settings_Fragment
import com.example.to_do.fragmets.task.Task_Fragment

class MainActivity : AppCompatActivity() {
    val taskFragment  = Task_Fragment()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListners()
        FragmentsConroller(taskFragment)
         binding.BottomNavigationView.setOnItemSelectedListener {  item ->
            when(item.itemId){

                R.id.List->  FragmentsConroller(taskFragment)
                R.id.Settings ->  FragmentsConroller(Settings_Fragment())

            }
             return@setOnItemSelectedListener true

         }





    }




    fun FragmentsConroller(fragment: Fragment)
    {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.FragmentsContainer,fragment)
            .addToBackStack("")
            .commit()

    }



    fun initListners(){
        binding.AddTask.setOnClickListener {
            val createTask = Bottom_Sheet_Task{
                println("Data Added")
                if(taskFragment.isVisible ){
                    taskFragment.RefreshTask()

                }

              }
            createTask.show(supportFragmentManager,"")

        }
    }



}