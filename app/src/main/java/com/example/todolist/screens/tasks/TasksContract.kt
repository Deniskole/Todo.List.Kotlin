package com.example.todolist.screens.tasks

import com.example.todolist.R
import com.example.todolist.model.Task

interface TasksContract {
    interface View {
        fun showData(tasksList: List<Task>)
    }

    interface Presenter {
        fun start()
        fun insert(title: String?, description: String)
        fun update(id: String, title: String?, description: String, favorite: Boolean)
        fun toggleFavorite(task: Task)
        fun remove(task: Task)
    }

    interface Storage {
        suspend fun getTasks(filter: Filter): List<Task>
        suspend fun insertTask(title: String?, description: String): Task?
        suspend fun updateTask(task: Task)
        suspend fun deleteTask(task: Task)
        suspend fun favoriteTask(task: Task, isFavorite: Boolean)

        enum class Filter { ALL, FAVORITE }
    }

    enum class Action(val titleResId: Int) {
        NEW(R.string.new_task),
        EDIT(R.string.edit),
        DELETE(R.string.delete)
    }
}