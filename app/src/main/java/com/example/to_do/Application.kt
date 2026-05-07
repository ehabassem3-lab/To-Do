package com.example.to_do

import android.app.Application
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.to_do.database.Data_Base
import com.example.to_do.fragmets.task.Task_Fragment

class Application  : Application(){
    override fun onCreate() {
        var task = Task_Fragment()
        super.onCreate()
        Data_Base.createDataBase(this)

    }
}