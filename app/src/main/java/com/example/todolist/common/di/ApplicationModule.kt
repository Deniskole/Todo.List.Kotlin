package com.example.todolist.common.di

import android.app.Application
import android.content.Context
import com.example.todolist.data.AppDatabase
import com.example.todolist.screens.tasks.TasksContract
import com.example.todolist.screens.tasks.storage.TasksDatabaseStorage
import com.example.todolist.screens.tasks.storage.TasksFireBaseStorage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import toothpick.config.Module
import javax.inject.Inject
import javax.inject.Provider

class ApplicationModule(application: Application) : Module() {
    init {
        bind(Context::class.java).toInstance(application)
        bind(TasksContract.Storage::class.java).to(TasksDatabaseStorage::class.java).singleton()
        bind(AppDatabase::class.java)
            .toProvider(AppDataBaseProvider::class.java)
            .providesSingleton()
        bind(TasksContract.Storage::class.java).to(TasksFireBaseStorage::class.java).singleton()
        bind(DatabaseReference::class.java).toInstance(FirebaseDatabase.getInstance().reference)
    }
}

class AppDataBaseProvider @Inject constructor(
    private val context: Context
) : Provider<AppDatabase> {
    override fun get() = AppDatabase.getDatabase(context)
}

