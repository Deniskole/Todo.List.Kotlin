package com.example.todolist.fragments.facade

import android.util.Log
import com.example.todolist.data.AppDatabase
import com.example.todolist.model.Task
import com.example.todolist.screens.tasks.TasksContract

class TasksStorageImpl(
    private val db: AppDatabase
) : TasksContract.TasksStorage {
    override suspend fun getTasks(filter: TasksContract.TasksStorage.Filter): List<Task> {
        return when (filter) {
            TasksContract.TasksStorage.Filter.ALL -> {
                Log.i("check", "DB DB")
                db.taskDao().getAllTasks()
            }
            TasksContract.TasksStorage.Filter.FINISHED -> {
                db.taskDao().getDoneTasks()
            }
        }
    }

    override suspend fun insertTask(task: Task) {
        db.taskDao().insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        db.taskDao().deleteTask(task)
    }
}


