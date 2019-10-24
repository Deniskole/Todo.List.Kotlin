package com.example.todolist.common.di.scenes

import com.example.todolist.screens.tasks.TasksContract
import com.example.todolist.screens.tasks.TasksFragment
import com.example.todolist.screens.tasks.TasksPresenter
import toothpick.config.Module

class TasksModule(fragment: TasksFragment) : Module() {
    init {
        bind(TasksContract.View::class.java).toInstance(fragment)
        bind(TasksContract.Presenter::class.java).to(TasksPresenter::class.java).singleton()
    }
}

