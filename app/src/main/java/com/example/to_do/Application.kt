package com.example.to_do

import android.app.Application
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.to_do.database.Data_Base
import com.example.to_do.fragmets.task.Task_Fragment
import contact_app.database.DataBaseContacts

class Application  : Application(){
    override fun onCreate() {
        super.onCreate()
        Data_Base.createDataBase(this)
        DataBaseContacts.createDataBase(this)

    }
}