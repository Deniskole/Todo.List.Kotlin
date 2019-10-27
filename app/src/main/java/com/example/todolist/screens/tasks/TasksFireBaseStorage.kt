package com.example.todolist.screens.tasks

import android.util.Log
import com.example.todolist.extension.await
import com.example.todolist.model.Task
import com.example.todolist.model.TaskFireBase
import com.google.firebase.database.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class TasksFireBaseStorage @Inject constructor() :
    TasksContract.Storage {

    var taskList: MutableList<TaskFireBase> = mutableListOf()

    private var reff: DatabaseReference = FirebaseDatabase
        .getInstance().reference


    // TODO: debug button delete later
    fun insertTest() {
        //Read All Data
        GlobalScope.launch {
            val result = reff.await()
            if (result.exists()) {
                for (dataSnapsShot in result.children) {
                    val task = dataSnapsShot.getValue(TaskFireBase::class.java)
                    if (task != null) {
                        task.id = dataSnapsShot.key.toString()
                        taskList.add(task)
                        Log.d("TAG", task.id)
                    }
                }
            }
        }
        //Read All Data
    }


    override suspend fun getTasks(filter: TasksContract.Storage.Filter): List<Task> {

        //Read All Data
        GlobalScope.launch {
            val result = reff.await()
            if (result.exists()) {
                for (dataSnapsShot in result.children) {
                    val task = dataSnapsShot.getValue(TaskFireBase::class.java)
                    if (task != null) {
                        task.id = dataSnapsShot.key.toString()
                        taskList.add(task)
                        Log.d("TAG", task.id)
                    }
                }
            }
        }
        //Read All Data

        //Change correct type!
        return taskList as List<Task>
    }

    override suspend fun insertTask(task: Task) {
        val id = reff.push().key.toString()
        val task3 = TaskFireBase(id, task.title, task.descriptions, task.favorite)

        GlobalScope.launch {
            reff.child(id).setValue(task3).await()
        }
    }

    override suspend fun updateTask(task: Task) {
        reff.child("tasks").child(task.id.toString()).setValue(task)
    }

    override suspend fun deleteTask(task: Task) {
        reff.child(task.id.toString()).removeValue()
    }

    override suspend fun favoriteTask(id: Int, favorite: Boolean) {
        reff.child(id.toString()).child("favorite").setValue(true)
    }
}




