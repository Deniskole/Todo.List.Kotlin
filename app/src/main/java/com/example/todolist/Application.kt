package com.example.todolist

import android.app.Application
import com.example.todolist.common.di.ApplicationModule
import toothpick.Toothpick

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        val scope = Toothpick.openScope(this)
        scope.installModules(ApplicationModule(this))
    }
}