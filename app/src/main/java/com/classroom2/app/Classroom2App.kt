package com.classroom2.app

import android.app.Application
import com.classroom2.app.data.remote.FirebaseInitializer
import com.classroom2.app.data.remote.ServiceLocator

class Classroom2App : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseInitializer.init(this)
        ServiceLocator.init()
    }
}
