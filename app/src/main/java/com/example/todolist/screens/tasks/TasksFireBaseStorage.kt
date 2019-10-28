package com.example.todolist.screens.tasks

import com.example.todolist.extension.await
import com.example.todolist.model.Task
import com.example.todolist.model.TaskFireBase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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
//                        Log.d("TAG", task.id)
                    }
                }
            }
        }
        return taskList
    }

    override suspend fun insertTask(task: Task) {
        val id = reff.push().key.toString()
        val task3 = TaskFireBase(id, task.title, task.descriptions, task.favorite)

        GlobalScope.launch {
            reff.child(id).setValue(task3).await()
        }
    }

    override suspend fun updateTask(task: Task) {
        GlobalScope.launch {
            reff.child(task.id.toString()).setValue(task).await()
        }
    }

    override suspend fun deleteTask(task: Task) {
        GlobalScope.launch {
            reff.child(task.id.toString()).removeValue().await()
        }
    }

    override suspend fun favoriteTask(task: Task) {
//        GlobalScope.launch {
//            reff.child(id).child("favorite").setValue(true).await()
//        }
    }
}