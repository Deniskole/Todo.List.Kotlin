package com.example.todolist.common.di

import android.app.Application
import com.example.todolist.data.AppDatabase
import toothpick.config.Module

class ApplicationModule(application: Application): Module() {
    init {
        val appDatabase = AppDatabase.getDatabase(application)
        bind(AppDatabase::class.java).toInstance(appDatabase)
    }
}