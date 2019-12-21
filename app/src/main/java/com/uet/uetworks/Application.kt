package com.uet.uetworks

import android.app.Application

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        MySharedPreferences.getInstance(this)
    }

}