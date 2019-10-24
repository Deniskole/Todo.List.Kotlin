package com.example.todolist.common.di

import android.app.Application
import android.content.Context
import com.example.todolist.data.AppDatabase
import com.example.todolist.screens.tasks.TasksContract
import com.example.todolist.screens.tasks.TasksDatabaseStorage
import toothpick.config.Module
import javax.inject.Inject
import javax.inject.Provider

class ApplicationModule(application: Application) : Module() {
    init {
        bind(TasksContract.Storage::class.java).to(TasksDatabaseStorage::class.java).singleton()
        bind(Context::class.java).toInstance(application)
        bind(AppDatabase::class.java)
            .toProvider(AppDataBaseProvider::class.java)
            .providesSingleton()
    }
}

class AppDataBaseProvider @Inject constructor(
    private val context: Context
) : Provider<AppDatabase> {
    override fun get() = AppDatabase.getDatabase(context)
}