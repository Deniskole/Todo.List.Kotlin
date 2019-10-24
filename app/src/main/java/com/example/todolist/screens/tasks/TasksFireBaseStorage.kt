package com.example.todolist.screens.tasks

import com.example.todolist.model.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TasksFireBaseStorage : TasksContract.Storage {

    private lateinit var reff: DatabaseReference

    override suspend fun getTasks(filter: TasksContract.Storage.Filter): List<Task> {

        val list = mutableListOf<Task>()

        return list
    }

    override suspend fun insertTask(task: Task) {

        reff = FirebaseDatabase.getInstance().reference.child("tasks")
        val id: String? = reff.push().key
        reff.child(id!!).setValue(task)

    }

    override suspend fun updateTask(task: Task) {

    }

    override suspend fun deleteTask(task: Task) {

    }

    override suspend fun favoriteTask(id: Int, favorite: Boolean) {

    }
}




