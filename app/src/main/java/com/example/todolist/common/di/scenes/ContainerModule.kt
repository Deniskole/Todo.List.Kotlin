package com.example.todolist.common.di.scenes

import com.example.todolist.screens.tasks.TasksContract
import com.example.todolist.screens.tasks.TasksFragment
import toothpick.config.Module

class ContainerModule(fragment: TasksFragment) : Module() {
    init {
        bind(TasksContract.Storage.Filter::class.java).toInstance(fragment.filter)
    }
}