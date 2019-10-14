package com.example.todolist.screens.tasks

import com.example.todolist.model.Task

interface TasksContract {
    interface View {
        fun showData(tasksList: List<Task>)
    }

    interface Presenter {
        fun start()

        fun allButtonDidPress()
        fun finishedButtonDidPress()
        fun insert(title: String?, description: String)
        fun update(title: String?, description: String, favorite: Boolean)
        fun delete(task: Task)

        fun filterMode(filter: Boolean)
    }

    interface TasksStorage {
        suspend fun getTasks(filter: Filter): List<Task>
        suspend fun insertTask(task: Task)
        suspend fun updateTask(task: Task)
        suspend fun deleteTask(task: Task)

        fun setFilterMode(filter: Filter)
        fun getFilterMode(): Filter

        enum class Filter { ALL, FINISHED }
    }
}