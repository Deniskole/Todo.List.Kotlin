package com.example.todolist.screens.tasks

import com.example.todolist.model.Task

interface TasksContract {
    interface TasksView {
        fun showData(tasksList: List<Task>)
    }

    interface TasksStorage {
        suspend fun getTasks(filter: Filter): List<Task>
        suspend fun insertTask(task: Task)
        suspend fun deleteTask(task: Task)

        enum class Filter { ALL, FINISHED }
    }
}