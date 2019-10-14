package com.example.todolist.screens.tasks

import com.example.todolist.R

enum class TaskAction(val titleResId: Int) {
    NEW(R.string.new_task),
    EDIT(R.string.edit),
    DELETE(R.string.delete)
}