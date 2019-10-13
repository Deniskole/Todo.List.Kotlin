package com.example.todolist.fragments.facade

import com.example.todolist.data.AppDatabase
import com.example.todolist.model.Task
import com.example.todolist.screens.tasks.TasksContract
import com.example.todolist.screens.tasks.TasksContract.TasksStorage.Filter.ALL
import com.example.todolist.screens.tasks.TasksContract.TasksStorage.Filter.FINISHED

class TasksStorageImpl(
    private val db: AppDatabase
) : TasksContract.TasksStorage {
    override suspend fun getTasks(filter: TasksContract.TasksStorage.Filter): List<Task> {
        return when (filter) {
            ALL -> db.taskDao().getAllTasks()
            FINISHED -> db.taskDao().getDoneTasks()
        }
    }

    override suspend fun insertTask(task: Task) = db.taskDao().insertTask(task)
    override suspend fun deleteTask(task: Task) = db.taskDao().deleteTask(task)
}


