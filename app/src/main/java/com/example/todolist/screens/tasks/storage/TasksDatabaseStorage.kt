package com.example.todolist.screens.tasks.storage

import com.example.todolist.data.AppDatabase
import com.example.todolist.model.Task
import com.example.todolist.screens.tasks.TasksContract
import com.example.todolist.screens.tasks.TasksContract.Storage.Filter.ALL
import com.example.todolist.screens.tasks.TasksContract.Storage.Filter.FAVORITE
import java.util.*
import javax.inject.Inject


class TasksDatabaseStorage @Inject constructor(db: AppDatabase) :
    TasksContract.Storage {
    private val dao = db.taskDao()

    override suspend fun getTasks(filter: TasksContract.Storage.Filter): List<Task> {
        return when (filter) {
            ALL -> dao.getAllTasks()
            FAVORITE -> dao.getDoneTasks()
        }
    }

    override suspend fun insertTask(title: String?, description: String) =
        Task(UUID.randomUUID().toString(), title, description).also {
            dao.insertTask(it)
        }

    override suspend fun updateTask(task: Task) = dao.updateTask(task.id, task.title, task.description)
    override suspend fun favoriteTask(task: Task) = dao.favoriteTask(task.id, task.favorite)
    override suspend fun deleteTask(task: Task) = dao.deleteTask(task)
}


