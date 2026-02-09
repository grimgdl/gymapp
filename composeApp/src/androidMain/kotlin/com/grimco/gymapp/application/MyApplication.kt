package com.grimco.gymapp.application

import android.app.Application
import com.grimco.gymapp.data.di.getModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(getModule())
        }
    }
}