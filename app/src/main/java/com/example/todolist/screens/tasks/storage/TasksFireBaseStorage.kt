package com.example.todolist.screens.tasks.storage

import com.example.todolist.extension.await
import com.example.todolist.model.Task
import com.example.todolist.screens.tasks.TasksContract
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject


class TasksFireBaseStorage @Inject constructor(private val reff: DatabaseReference) :
    TasksContract.Storage {

    private val taskList = mutableListOf<Task>()

    override suspend fun getTasks(filter: TasksContract.Storage.Filter): List<Task> {
        taskList.clear()
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
        val task3 = Task(task.id, task.title, task.description, task.favorite)
        reff.child(task.id).setValue(task3).await()
    }

    override suspend fun updateTask(task: Task) {
        reff.child(task.id).setValue(task).await()
    }

    override suspend fun deleteTask(task: Task) {
        reff.child(task.id).removeValue().await()
    }

    override suspend fun favoriteTask(task: Task) {
        reff.child(task.id).child("favorite").setValue(task.favorite).await()
    }
}