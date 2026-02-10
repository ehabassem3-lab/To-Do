package com.example.to_do

import android.app.Application
import com.example.to_do.database.Data_Base

class Application  : Application(){
    override fun onCreate() {
        super.onCreate()
        Data_Base.createDataBase(this)
    }
}