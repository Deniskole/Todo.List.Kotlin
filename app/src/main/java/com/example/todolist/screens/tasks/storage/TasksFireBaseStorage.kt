package com.example.todolist.screens.tasks.storage

import com.example.todolist.extension.await
import com.example.todolist.model.Task
import com.example.todolist.screens.tasks.TasksContract
import com.example.todolist.util.Constants.PATH_FAVORITE
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject


class TasksFireBaseStorage @Inject constructor(private val reff: DatabaseReference) :
    TasksContract.Storage {

    override suspend fun getTasks(filter: TasksContract.Storage.Filter): List<Task> {
        val taskList = mutableListOf<Task>()
        val result = reff.await()
        if (result.exists()) {
            for (dataSnapsShot in result.children) {
                val task = dataSnapsShot.getValue(Task::class.java)
                if (task != null) {
                    task.id = dataSnapsShot.key ?: ""
                    taskList.add(task)
                }
            }
        }
        return when (filter) {
            TasksContract.Storage.Filter.ALL -> {
                taskList
            }
            TasksContract.Storage.Filter.FAVORITE -> {
                taskList.filter { it.favorite }
            }
        }
    }

    override suspend fun insertTask(task: Task) {
        reff.child(task.id).setValue(task).await()
    }

    override suspend fun updateTask(task: Task) {
        reff.child(task.id).setValue(task).await()
    }

    override suspend fun deleteTask(task: Task) {
        reff.child(task.id).removeValue().await()
    }

    override suspend fun favoriteTask(task: Task) {
        reff.child(task.id).child(PATH_FAVORITE).setValue(task.favorite).await()
    }
}