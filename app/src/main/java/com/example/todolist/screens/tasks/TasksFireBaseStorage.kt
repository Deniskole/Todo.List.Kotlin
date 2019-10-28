package com.example.todolist.screens.tasks

import com.example.todolist.extension.await
import com.example.todolist.model.Task
import com.example.todolist.model.TaskFireBase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.todolist.screens.tasks.Task as TaskSealed


class TasksFireBaseStorage : TasksContract.Storage {

    val taskList = mutableListOf<Task>()

    private var reff: DatabaseReference = FirebaseDatabase
        .getInstance().reference

    override suspend fun getTasks(filter: TasksContract.Storage.Filter): List<Task> {

        GlobalScope.launch {
            val result = reff.await()
            if (result.exists()) {
                for (dataSnapsShot in result.children) {
                    val task = dataSnapsShot.getValue(TaskFireBase::class.java)
                    if (task != null) {
                        task.id = dataSnapsShot.key.toString()
                        taskList.add(task as Task)
                    }
                }
            }
        }
        return taskList
    }

    override suspend fun insertTask(task: TaskSealed) {
        if (task is TaskSealed.Firebase) {
            val id = reff.push().key.toString()
            val task3 = TaskSealed.Firebase(id, task.title, task.description, task.favorite)

            GlobalScope.launch {
                reff.child(id).setValue(task3).await()
            }
        }
    }

    override suspend fun updateTask(task: TaskSealed) {
        if (task is TaskSealed.Firebase) {
            GlobalScope.launch {
                reff.child(task.id).setValue(task).await()
            }
        }
    }

    override suspend fun deleteTask(task: TaskSealed) {
        if (task is TaskSealed.Firebase) {
            GlobalScope.launch {
                reff.child(task.id).removeValue().await()
            }
        }
    }

    override suspend fun favoriteTask(task: TaskSealed) {
        if (task is TaskSealed.Firebase) {
            GlobalScope.launch {
                reff.child(task.id).child("favorite").setValue(true).await()
            }
        }
    }
}