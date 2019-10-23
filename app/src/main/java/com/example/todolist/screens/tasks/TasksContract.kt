package com.example.todolist.screens.tasks

import com.example.todolist.R
import com.example.todolist.model.Task

interface TasksContract {
    interface View {
        fun showData(tasksList: List<Task>)
    }

    interface Presenter {
        fun start()
        // TODO: Naming. Should not be the same as in the storage.
        //  It is different operation and should have business logic naming.
        fun insert(title: String?, description: String)
        fun update(id: Int, title: String?, description: String, favorite: Boolean)
        fun favorite(id: Int, favorite: Boolean)
        fun remove(task: Task)
    }

    interface Storage {
        suspend fun getTasks(filter: Filter): List<Task>
        suspend fun insertTask(task: Task)
        suspend fun updateTask(task: Task)
        suspend fun deleteTask(task: Task)
        suspend fun favoriteTask(id: Int, favorite: Boolean)

        enum class Filter { ALL, FAVORITE }
    }

    enum class Action(val titleResId: Int) {
        NEW(R.string.new_task),
        EDIT(R.string.edit),
        DELETE(R.string.delete)
    }
}