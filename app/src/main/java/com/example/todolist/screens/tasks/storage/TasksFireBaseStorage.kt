package com.example.todolist.screens.tasks.storage

import android.util.Log
import com.example.todolist.extension.await
import com.example.todolist.model.Task
import com.example.todolist.screens.tasks.TasksContract
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TasksFireBaseStorage : TasksContract.Storage {

    private val taskList = mutableListOf<Task>()
    private var reff: DatabaseReference = FirebaseDatabase.getInstance().reference

    //Temp delete
    fun clickTest() {
        val id = reff.push().key.toString()
        Log.d("TAG", id)
    }

    override suspend fun getTasks(filter: TasksContract.Storage.Filter): List<Task> {
        taskList.clear()
        GlobalScope.launch {
            val result = reff.await()
            if (result.exists()) {
                for (dataSnapsShot in result.children) {
                    val task = dataSnapsShot.getValue(Task::class.java)
                    if (task != null) {
                        task.id = dataSnapsShot.key.toString()
                        taskList.add(task)
                        Log.d("TAG", task.id)
                    }
                }
            }
        }
        return taskList
    }

    override suspend fun insertTask(task: Task) {
        val id = reff.push().key.toString()
        val task3 = Task(id, task.title, task.descriptions, task.favorite)

        GlobalScope.launch {
            reff.child(id).setValue(task3).await()
        }
    }

    override suspend fun updateTask(task: Task) {
        GlobalScope.launch {
            reff.child(task.id).setValue(task).await()
        }
    }

    override suspend fun deleteTask(task: Task) {
        GlobalScope.launch {
            reff.child(task.id).removeValue().await()
        }
    }

    override suspend fun favoriteTask(task: Task) {
        GlobalScope.launch {
            reff.child(task.id).child("favorite").setValue(true).await()
        }
    }
}