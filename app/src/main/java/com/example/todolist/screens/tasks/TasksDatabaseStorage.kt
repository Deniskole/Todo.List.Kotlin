package com.example.todolist.screens.tasks

import com.example.todolist.data.AppDatabase
import com.example.todolist.model.Task
import com.example.todolist.screens.tasks.TasksContract.Storage.Filter.ALL
import com.example.todolist.screens.tasks.TasksContract.Storage.Filter.FAVORITE
import javax.inject.Inject


class TasksDatabaseStorage @Inject constructor(private val db: AppDatabase) : TasksContract.Storage {

    override suspend fun getTasks(filter: TasksContract.Storage.Filter): List<Task> {
        return when (filter) {
            ALL -> db.taskDao().getAllTasks()
            FAVORITE -> db.taskDao().getDoneTasks()
        }
    }

    override suspend fun insertTask(task: Task) = db.taskDao().insertTask(task)

    override suspend fun updateTask(task: Task) =
        db.taskDao().updateTask(task.id, task.title, task.descriptions, task.favorite)

    override suspend fun favoriteTask(id: Int, favorite: Boolean) =
        db.taskDao().favoriteTask(id, favorite)

    override suspend fun deleteTask(task: Task) = db.taskDao().deleteTask(task)
}


