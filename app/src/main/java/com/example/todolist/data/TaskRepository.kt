package com.example.todolist.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.todolist.data.TaskDao
import com.example.todolist.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }
}
