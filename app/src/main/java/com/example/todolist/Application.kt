package com.example.todolist

import android.app.Application
import toothpick.Toothpick

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        val scope = Toothpick.openScope(this)
        scope.installModules(
            //Implement
        )
    }
}