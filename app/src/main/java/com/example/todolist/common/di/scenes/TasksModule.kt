package com.example.todolist.common.di.scenes

import com.example.todolist.screens.tasks.TasksContract
import com.example.todolist.screens.tasks.TasksFragment
import com.example.todolist.screens.tasks.TasksPresenter
import com.example.todolist.screens.tasks.TasksStorage
import toothpick.config.Module

class TasksModule(fragment: TasksFragment) : Module() {
    init {
        bind(TasksContract.Storage::class.java).to(TasksStorage::class.java).singleton()
        bind(TasksContract.View::class.java).toInstance(fragment)
        bind(TasksContract.Presenter::class.java).to(TasksPresenter::class.java).singleton()
    }
}