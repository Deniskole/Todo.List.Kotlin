package com.example.todolist.screens.tasks

import com.example.todolist.R
import com.example.todolist.model.Task

interface TasksContract {
    interface View {
        fun showData(tasksList: List<Task>)
    }

    interface Presenter {
        fun show(filter: TasksStorage.Filter)
        fun buttonDidPress(filter: TasksStorage.Filter)
        fun insert(title: String?, description: String)
        fun update(id: Int?, title: String?, description: String, favorite: Boolean)
        fun delete(task: Task)
    }

    /* TODO: Naming issue. */
    interface TasksStorage {
        suspend fun getTasks(filter: Filter): List<Task>
        suspend fun insertTask(task: Task)
        suspend fun updateTask(task: Task)
        suspend fun deleteTask(task: Task)

        enum class Filter { ALL, FINISHED }
    }

    /* TODO: Naming issue. */
    enum class TaskAction(val titleResId: Int) {
        NEW(R.string.new_task),
        EDIT(R.string.edit),
        DELETE(R.string.delete)
    }
}