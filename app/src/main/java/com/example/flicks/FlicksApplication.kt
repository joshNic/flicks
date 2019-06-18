package com.example.flicks

import android.app.Application
import android.content.ContextWrapper

private lateinit var INSTANCE: Application

class FlicksApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}
object AppContext : ContextWrapper(INSTANCE)