package com.example.todolist.common.di

import android.content.Context
import com.example.todolist.data.AppDatabase
import com.example.todolist.screens.tasks.TasksContract
import com.example.todolist.screens.tasks.TasksPresenter
import com.example.todolist.storage.TasksStorage
import toothpick.config.Module

class TasksModule(view: TasksContract.View, context: Context) : Module() {
    init {
        val dataBase: AppDatabase = AppDatabase.getDatabase(context)
        bind(TasksPresenter::class.java).toInstance(
            TasksPresenter(view, TasksStorage(dataBase, context))
        )
    }
}