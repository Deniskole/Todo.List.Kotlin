package com.example.todolist

enum class TaskAction(val titleResId: Int) {
    NEW(R.string.new_task),
    EDIT(R.string.edit),
    DELETE(R.string.delete)
}